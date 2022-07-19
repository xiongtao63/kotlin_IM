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


class ContactsFragment : BaseFragment(), ContactsContract.View {
    val presenter by lazy {
        ContactsPresenter(this)
    }
    override fun getLayoutResId(): Int  = R.layout.fragment_contacts

    @SuppressLint("ResourceAsColor")
    override fun init() {
        super.init()
        header_title.text = getString(R.string.contract)
        add.visibility = View.VISIBLE

        add.setOnClickListener { addFriend() }

        swipeRefreshLayout.apply {
            setColorSchemeColors(R.color.purple_200)
            isRefreshing = true
            setOnRefreshListener {
                presenter.loadData()
            }

        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = activity?.let { ContractAdapter(it,presenter.contactListItems) }
        }
        presenter.loadData()


        EMClient.getInstance().contactManager().setContactListener(object :
            EMContactListenerAdapter() {
            override fun onContactDeleted(username: String?) {
                presenter.loadData()
            }
        })


    }



    private fun addFriend() {
//        presenter.addFriend()
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
}