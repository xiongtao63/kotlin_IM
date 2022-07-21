package com.xiongtao.im.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.xiongtao.im.R
import com.xiongtao.im.adapter.ConversationAdapter
import kotlinx.android.synthetic.main.fragment_conversation.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ConversationFragment : BaseFragment() {

    val conversations = mutableListOf<EMConversation>()
    val messageListener = object : EMMessageListener{
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            loadConversation()
        }

    }
    override fun getLayoutResId(): Int  = R.layout.fragment_conversation

    override fun init() {
        super.init()
        initHeader()
        initRecycleView()
    }

    private fun initRecycleView() {
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ConversationAdapter(context, conversations)
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
//        loadConversation()
    }

    private fun loadConversation() {

        doAsync {
            conversations.clear()
            val allConversations = EMClient.getInstance().chatManager().allConversations
            conversations.addAll(allConversations.values)
            uiThread { recycler_view.adapter?.notifyDataSetChanged() }
        }

    }

    private fun initHeader() {
        header_title.text = "消息"
    }

    override fun onResume() {
        super.onResume()
        loadConversation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}