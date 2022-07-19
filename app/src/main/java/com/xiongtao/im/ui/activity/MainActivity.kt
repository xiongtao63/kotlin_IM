package com.xiongtao.im.ui.activity

import com.xiongtao.im.R
import com.xiongtao.im.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            FragmentFactory.instances.getFragment(tabId)
                ?.let { beginTransaction.replace(R.id.contentContainer, it) }
            beginTransaction.commit()
        }
    }

}