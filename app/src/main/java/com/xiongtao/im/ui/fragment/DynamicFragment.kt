package com.xiongtao.im.ui.fragment

import com.hyphenate.chat.EMClient
import com.xiongtao.im.R
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.header.*
import com.hyphenate.EMCallBack
import com.xiongtao.im.adapter.EMCallBackAdapter
import com.xiongtao.im.ui.activity.LoginActivity
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class DynamicFragment : BaseFragment() {
    override fun getLayoutResId(): Int  = R.layout.fragment_dynamic

    override fun init() {
        super.init()
        header_title.text = getString(R.string.dynamic)
        val userName = String.format(getString(R.string.logout), EMClient.getInstance().currentUser)
        logout.text = userName
        logout.setOnClickListener { logout() }
    }

    fun logout(){
        EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
            override fun onSuccess() {
                context?.startActivity<LoginActivity>()
                activity?.finish()
            }

            override fun onError(p0: Int, p1: String?) {
                context?.runOnUiThread { toast(R.string.logout_failed) }
            }
        })
    }
}