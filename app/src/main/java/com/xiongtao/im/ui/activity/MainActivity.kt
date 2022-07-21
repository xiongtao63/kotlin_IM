package com.xiongtao.im.ui.activity

import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.xiongtao.im.R
import com.xiongtao.im.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int = R.layout.activity_main

    val messagelistener = object : EMMessageListener{
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            updateBottomcount()
        }

    }

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            FragmentFactory.instances.getFragment(tabId)
                ?.let { beginTransaction.replace(R.id.contentContainer, it) }
            beginTransaction.commit()
        }
        EMClient.getInstance().chatManager().addMessageListener(messagelistener)
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener{
            override fun onConnected() {
            }

            override fun onDisconnected(p0: Int) {
                if(p0 == EMError.USER_LOGIN_ANOTHER_DEVICE){
                    startActivity<LoginActivity>()
                    finish()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        updateBottomcount()
    }

    private fun updateBottomcount() {
        //初始化消息未读的个数
        val tabWithId = bottomBar.getTabWithId(R.id.tab_conversation) ?: return
        tabWithId.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messagelistener)
    }

}