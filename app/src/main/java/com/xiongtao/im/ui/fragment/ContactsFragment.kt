package com.xiongtao.im.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiongtao.im.R
import com.xiongtao.im.adapter.ContractAdapter
import com.xiongtao.im.contract.ContactsContract
import com.xiongtao.im.presenter.ContactsPresenter
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast
import com.hyphenate.EMContactListener

import com.hyphenate.chat.EMClient
import com.xiongtao.im.adapter.EMContactListenerAdapter
import com.xiongtao.im.ui.activity.AddFriendActivity
import com.xiongtao.im.widget.OnSliderTouchListener
import org.jetbrains.anko.startActivity


class ContactsFragment : BaseFragment(), ContactsContract.View {
    val presenter by lazy {
        ContactsPresenter(this)
    }

    val listener = object :
        EMContactListenerAdapter() {
        override fun onContactDeleted(username: String?) {
            presenter.loadData()
        }

        override fun onContactAdded(username: String?) {
            presenter.loadData()
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_contacts


    override fun init() {
        super.init()
        initHeader()
        initSwipeRefreshLayout()
        initRecycleView()
        initSlidBar()
        presenter.loadData()

        EMClient.getInstance().contactManager().setContactListener(listener)

    }

    private fun initSlidBar() {
        slideBar.onTouchListener = object : OnSliderTouchListener {
            override fun onShowChar(string: String, index: Int) {
                section.text = string
                section.visibility = View.VISIBLE
                recyclerView.scrollToPosition(index)
            }

            override fun onHideChar() {
                section.visibility = View.GONE
            }

        }
    }

    private fun initRecycleView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = activity?.let { ContractAdapter(it, presenter.contactListItems) }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.apply {
            setColorSchemeColors(R.color.purple_200)
            isRefreshing = true
            setOnRefreshListener {
                presenter.loadData()
            }

        }
    }

    private fun initHeader() {
        header_title.text = getString(R.string.contract)
        add.visibility = View.VISIBLE
        add.setOnClickListener { context?.startActivity<AddFriendActivity>() }
    }

    override fun onStartLoad() {

    }

    override fun onLoadSuccess() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onLoadFailed() {
        swipeRefreshLayout.isRefreshing = false
        context?.toast(getString(R.string.load_failed))
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().contactManager().removeContactListener(listener)
    }
}