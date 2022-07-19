package com.xiongtao.im.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private val progreddDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

    fun hideSoftKeyBoard(){
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
    }
}