package com.xiongtao.im.presenter

import com.hyphenate.chat.EMClient
import com.xiongtao.im.adapter.EMCallBackAdapter
import com.xiongtao.im.contract.LoginContract
import com.xiongtao.im.extenstions.isValidPassword
import com.xiongtao.im.extenstions.isValidUserName

class LoginPresenter(val view: LoginContract.View): LoginContract.Presenter {
    override fun login(userName: String, password: String) {
        if(userName.isValidUserName()){
            if(password.isValidPassword()){
                //login
                view.onStartLogin()
                loginEasyMob(userName,password)
            }else view.onPassWordError()
        }else view.onUserNameError()
    }

    private fun loginEasyMob(userName: String, password: String) {
        EMClient.getInstance().login(userName,password,object : EMCallBackAdapter() {
            //子线程
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                //主线程更新
                uiThread { view.onLoginSuccess() }

            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.onLoginFailed() }
            }

        })
    }


}