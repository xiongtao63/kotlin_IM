package com.xiongtao.im.presenter

import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.xiongtao.im.adapter.EMCallBackAdapter
import com.xiongtao.im.contract.ChatContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ChatPresenter(val view:ChatContract.View): ChatContract.Presenter {

    companion object{
        val PAGE_SIZE = 10
    }
    val messages = mutableListOf<EMMessage>()
    override fun send(contact: String, message: String) {
        val emMessage = EMMessage.createTxtSendMessage(message, contact)
        emMessage.setMessageStatusCallback(object : EMCallBackAdapter() {
            override fun onSuccess() {
                uiThread { view.onSendSuccess() }
            }

            override fun onError(p0: Int, p1: String?) {
               uiThread { view.onSendFailed() }
            }
        })
        messages.add(emMessage)
        view.onStartSendMessage()
        EMClient.getInstance().chatManager().sendMessage(emMessage)
    }

    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        //加入当前的消息列表
        p0?.let { messages.addAll(it) }
        //更新消息已读消息
        val conversation = EMClient.getInstance().chatManager().getConversation(username)
        conversation.markAllMessagesAsRead()
    }

    override fun loadMessages(username: String) {
        doAsync {
            //获取聊天记录
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            //将加载的消息置为已读
            conversation.markAllMessagesAsRead()
            val allMessages = conversation.allMessages
            messages.addAll(allMessages)
            uiThread { view.onLoadedMessage() }

        }
    }

    override fun loadMoreMessages(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            val msgId = messages[0].msgId
            val moreMessages = conversation.loadMoreMsgFromDB(msgId, PAGE_SIZE)
            messages.addAll(0,moreMessages)
            uiThread { view.onLoadedMoreMessage(moreMessages.size) }

        }

    }

}