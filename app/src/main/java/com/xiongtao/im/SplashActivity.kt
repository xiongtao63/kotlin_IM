package com.xiongtao.im

import android.os.Handler
import android.os.Looper
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
        presenter.checkLoginStates()

    }

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun onNotLoggedIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
        }, DELAY)
    }

    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }

}