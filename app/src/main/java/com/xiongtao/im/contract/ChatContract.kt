package com.xiongtao.im.contract

import com.hyphenate.chat.EMMessage

interface ChatContract {
    interface  Presenter:BasePresenter{
        fun send(contact:String,message:String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
        fun loadMessages(username: String)
        fun loadMoreMessages(username: String)
    }
    interface View{
        fun onStartSendMessage()
        fun onSendSuccess()
        fun onSendFailed()
        fun onLoadedMessage()
        fun onLoadedMoreMessage(size: Int)

    }
}