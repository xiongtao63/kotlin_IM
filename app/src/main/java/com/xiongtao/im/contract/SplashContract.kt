package com.xiongtao.im.contract

interface SplashContract {
    interface Presenter : BasePresenter{
        fun checkLoginStates()
    }
    interface View {
        fun onNotLoggedIn()
        fun onLoggedIn()
    }
}