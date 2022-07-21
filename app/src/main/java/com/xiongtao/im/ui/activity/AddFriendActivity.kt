package com.xiongtao.im.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.xiongtao.im.R
import com.xiongtao.im.adapter.AddFriendAdapter
import com.xiongtao.im.contract.AddFriendContract
import com.xiongtao.im.presenter.AddFriendPresenter
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast

class AddFriendActivity : BaseActivity(),AddFriendContract.View {
    override fun getLayoutResId(): Int =  R.layout.activity_add_friend

    val presenter = AddFriendPresenter(this)

    override fun init() {
        super.init()

        header_title.text = getString(R.string.add)

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendAdapter(context, presenter.addFriendItems)
        }

        search.setOnClickListener { search() }
        user_name.setOnEditorActionListener { v, actionId, event ->
            search()
            true
        }


    }

    fun search(){
        hideSoftKeyBoard()
        showProgress(getString(R.string.searching))
        val key = user_name.text.trim().toString()
        presenter.search(key)
    }

    override fun onSearchSuccess() {
        dismissProgress()
        toast("搜索成功")
        recycler_view.adapter?.notifyDataSetChanged()

    }

    override fun onSearchFailed() {
        dismissProgress()
        toast("搜索失败")

    }


}