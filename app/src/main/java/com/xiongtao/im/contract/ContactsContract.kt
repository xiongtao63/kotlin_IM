package com.xiongtao.im.contract

interface ContactsContract {
    interface Presenter: BasePresenter{
        fun loadData()
    }
    interface View{
        fun onStartLoad()
        fun onLoadSuccess()
        fun onLoadFailed()

    }
}