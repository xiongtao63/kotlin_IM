package com.xiongtao.im.ui.activity

import com.xiongtao.im.R
import com.xiongtao.im.contract.RegisterContract
import com.xiongtao.im.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_login.passWord
import kotlinx.android.synthetic.main.activity_login.userName
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity: BaseActivity(),RegisterContract.View {

    override fun getLayoutResId(): Int  = R.layout.activity_register
    val presenter by lazy {
        RegisterPresenter(this)
    }

    override fun init() {
        super.init()
        register.setOnClickListener { register() }
        confirm_password.setOnEditorActionListener { _, _, _ ->
            register()
            true
        }
    }

    private fun register(){
        hideSoftKeyBoard()
        val userName = userName.text.trim().toString()
        val password = passWord.text.trim().toString()
        val confirmPassword = confirm_password.text.trim().toString()
        presenter.register(userName,password,confirmPassword)
    }

    override fun onUserNameError() {
        userName.error = getString(R.string.username_error)
    }

    override fun onPasswordError() {
        passWord.error = getString(R.string.password_error)
    }

    override fun onConfirmPasswordError() {
        confirm_password.error = getString(R.string.password_error)
    }

    override fun onStartRegister() {
        showProgress(getString(R.string.registering))
    }

    override fun onRegisterSuccess() {
        dismissProgress()
        toast(R.string.register_success)
        finish()
    }

    override fun onRegisterFailed() {
        dismissProgress()
        toast(R.string.register_failed)
    }

    override fun onExistRegister() {
        dismissProgress()
        toast(R.string.register_exist)
    }
}