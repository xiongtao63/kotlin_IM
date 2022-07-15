package com.xiongtao.im

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    open fun init() {
        //初始化公共功能，子类可以复写
    }

    abstract fun getLayoutResId(): Int
}