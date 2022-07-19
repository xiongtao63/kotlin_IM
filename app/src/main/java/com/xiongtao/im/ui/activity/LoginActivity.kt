package com.xiongtao.im.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.xiongtao.im.R
import com.xiongtao.im.contract.LoginContract
import com.xiongtao.im.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), LoginContract.View {

    val presenter = LoginPresenter(this)

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun init() {
        super.init()
        login.setOnClickListener { login() }
        passWord.setOnEditorActionListener { _, _, _ ->
            login()
            true
        }
        newRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

    private fun login() {
        //隐藏软键盘
        hideSoftKeyBoard()
        if (hasWritePermission()) {

            val userName = userName.text.trim().toString()
            val password = passWord.text.trim().toString()
            presenter.login(userName, password)
        } else applyWritePermission()

    }

    private fun applyWritePermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissions, 0)
    }

    private fun hasWritePermission(): Boolean {
        val result =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            login()
        }else toast(R.string.permission_denid)

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