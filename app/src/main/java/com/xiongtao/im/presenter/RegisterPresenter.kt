package com.xiongtao.im.presenter

import cn.bmob.v3.BmobUser
import com.xiongtao.im.contract.RegisterContract
import com.xiongtao.im.extenstions.isValidPassword
import com.xiongtao.im.extenstions.isValidUserName
import com.google.android.material.snackbar.Snackbar

import cn.bmob.v3.exception.BmobException

import cn.bmob.v3.listener.SaveListener
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class RegisterPresenter(val view:RegisterContract.View): RegisterContract.Presenter {
    override fun register(userName: String, password: String, confirmPassword: String) {
        if(userName.isValidUserName()){
            if(password.isValidPassword()){
                if(password == confirmPassword){
                    view.onStartRegister()
                    registerBmob(userName,password)
                }else view.onConfirmPasswordError()
            }else view.onPasswordError()

        }else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val user = BmobUser()
        user.username = userName
        user.setPassword(password)
        val saveListener = object : SaveListener<BmobUser>(){
            override fun done(p0: BmobUser?, e: BmobException?) {
                if (e == null) {
                    //注册成功
                    // 注册到环信服务器
                   registerEasyMob(userName,password)
                } else {
                    if (e.errorCode == 202) view.onExistRegister() else
                    view.onRegisterFailed()
                }
            }

        }
        user.signUp(saveListener)

    }

    private fun registerEasyMob(userName: String, password: String) {
        doAsync {
            try {
                //注册失败会抛出HyphenateException
                EMClient.getInstance().createAccount(userName, password);//同步方法
                //注册成功
                uiThread { view.onRegisterSuccess() }
            }catch (e: HyphenateException){
                //注册失败
                uiThread { view.onRegisterFailed() }
            }

        }

    }


}