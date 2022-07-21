package com.xiongtao.im.contract

interface AddFriendContract {
    interface Presenter: BasePresenter{
        fun search(key:String)
    }
    interface View{
        fun onSearchSuccess()
        fun onSearchFailed()
    }
}