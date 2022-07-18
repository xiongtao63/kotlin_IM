package com.xiongtao.im.contract

interface LoginContract {
    interface Presenter : BasePresenter{
        fun login(userName:String,password:String)
    }
    interface View{
        fun onUserNameError()
        fun onPassWordError()
        fun onStartLogin()
        fun onLoginSuccess()
        fun onLoginFailed()
    }
}