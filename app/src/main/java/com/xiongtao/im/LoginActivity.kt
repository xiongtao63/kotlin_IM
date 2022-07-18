package com.xiongtao.im

import com.xiongtao.im.contract.LoginContract
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(),LoginContract.View{

    override fun getLayoutResId(): Int = R.layout.activity_login

    init {
        userName.setOnClickListener {

        }
    }

    override fun onUserNameError() {
        userName.error = getString(R.string.username_error)
    }

    override fun onPassWordError() {
        passWord.error = getString(R.string.password_error)
    }

    override fun onStartLogin() {
        showProgress(getString(R.string.logining))
    }

    override fun onLoginSuccess() {
        //隐藏进度条，
        dismissProgress()
        //进入主界面
        startActivity<MainActivity>()
        //退出当前页面
        finish()
    }

    override fun onLoginFailed() {
        //隐藏进度条，
        dismissProgress()
        toast(R.string.login_failed)
    }
}