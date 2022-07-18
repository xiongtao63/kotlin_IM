package com.xiongtao.im

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    val progreddDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    open fun init() {
        //初始化公共功能，子类可以复写
    }

    abstract fun getLayoutResId(): Int

    fun showProgress(message:String){
        progreddDialog.setMessage(message)
        progreddDialog.show()
    }

    fun dismissProgress(){
        progreddDialog.dismiss()
    }
}