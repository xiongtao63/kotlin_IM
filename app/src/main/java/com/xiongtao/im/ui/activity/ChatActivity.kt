package com.xiongtao.im.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.xiongtao.im.R
import com.xiongtao.im.adapter.MessageListAdapter
import com.xiongtao.im.contract.ChatContract
import com.xiongtao.im.presenter.ChatPresenter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

class ChatActivity: BaseActivity(),ChatContract.View {
    override fun getLayoutResId(): Int = R.layout.activity_chat
    val messageListener = object :EMMessageListener{
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(username, p0)
            runOnUiThread {
                recycler_view.adapter?.notifyDataSetChanged()
                scrollToBottom()
            }
        }

    }
    val presenter = ChatPresenter(this)
    lateinit var username: String
    override fun init() {
        super.init()
        initHeader()
        initEdittext()
        initRecycleView()
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        send.setOnClickListener { send() }
        presenter.loadMessages(username)
    }

    private fun initRecycleView() {
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(context,presenter.messages)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    //当recyclerview是空闲状态
                    //检查是否到顶部，到顶部需要加载更多
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                        //如果第一个可见条目是0 ,则滑动到顶部
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        if(linearLayoutManager.findFirstVisibleItemPosition() == 0){
                            presenter.loadMoreMessages(username)
                        }
                    }
                }
            })
        }

    }

    private fun initEdittext() {
        message.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                send.isEnabled = !s.isNullOrEmpty()
            }

        })
        message.setOnEditorActionListener { v, actionId, event ->
            send()
            true
        }

    }
    fun send(){
        hideSoftKeyBoard()
        val message = message.text.trim().toString()
        presenter.send(username,message)
    }

    private fun initHeader() {
        back.visibility = View.VISIBLE
        back.setOnClickListener { finish() }
        username = intent.getStringExtra("userName").toString()
        header_title.text = String.format("与%s聊天中", username)

    }

    override fun onStartSendMessage() {
        recycler_view.adapter?.notifyDataSetChanged()
    }

    override fun onSendSuccess() {
        recycler_view.adapter?.notifyDataSetChanged()
        toast("发送成功")
        message.text.clear()
        scrollToBottom()
    }

    private fun scrollToBottom() {
        recycler_view.scrollToPosition(presenter.messages.size -1)
    }

    override fun onSendFailed() {
        toast("发送失败")
        recycler_view.adapter?.notifyDataSetChanged()
    }

    override fun onLoadedMessage() {
        recycler_view.adapter?.notifyDataSetChanged()
        scrollToBottom()
    }

    override fun onLoadedMoreMessage(size:Int) {
        recycler_view.adapter?.notifyDataSetChanged()
        recycler_view.scrollToPosition(size)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}