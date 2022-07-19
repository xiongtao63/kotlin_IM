package com.xiongtao.im.presenter

import com.hyphenate.chat.EMClient
import com.xiongtao.im.contract.SplashContract

class SplashPresenter(val view: SplashContract.View): SplashContract.Presenter {
    override fun checkLoginStates() {
        if(isLoginIn()) view.onLoggedIn() else view.onNotLoggedIn()
    }

    private fun isLoginIn(): Boolean {
        return EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
    }
}