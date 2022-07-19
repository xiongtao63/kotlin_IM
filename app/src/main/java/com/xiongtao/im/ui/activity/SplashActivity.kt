package com.xiongtao.im.ui.activity

import android.os.Handler
import android.os.Looper
import com.xiongtao.im.R
import com.xiongtao.im.contract.SplashContract
import com.xiongtao.im.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity(), SplashContract.View {
    companion object{
        val DELAY = 2000L
    }
    val presenter = SplashPresenter(this)

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    init {
        super.init()
        handler.postDelayed({
            presenter.checkLoginStates()
        },DELAY)
    }

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun onNotLoggedIn() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }

}