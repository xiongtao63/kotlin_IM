package com.xiongtao.im.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import com.xiongtao.im.R
import kotlinx.android.synthetic.main.view_send_message.view.*
import java.util.*

class SendMessageItemView(context: Context?, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {

    init {
            View.inflate(context, R.layout.view_send_message,this)
        }

    fun bindView(emMessage: EMMessage,showTimestamp: Boolean) {
        updateTimestamp(emMessage,showTimestamp)
        updateMessage(emMessage)
        updateProgress(emMessage)
    }

    private fun updateProgress(emMessage: EMMessage) {
        emMessage.status().let {
            when(it){
                EMMessage.Status.INPROGRESS -> {
                    send_message_progress.visibility = View.VISIBLE
                    send_message_progress.setImageResource(R.drawable.send_message_progress)
                    val animationDrawable = send_message_progress.drawable as AnimationDrawable
                    animationDrawable.start()
                }
                EMMessage.Status.SUCCESS -> send_message_progress.visibility = View.GONE
                EMMessage.Status.FAIL -> {
                    send_message_progress.visibility = View.VISIBLE
                    send_message_progress.setImageResource(R.mipmap.msg_error)
                }
            }
        }
    }

    private fun updateMessage(emMessage: EMMessage) {
        if(emMessage.type == EMMessage.Type.TXT){
            content.text = (emMessage.body as EMTextMessageBody).message
        }else{
            content.text = "非文本消息"
        }
    }

    private fun updateTimestamp(emMessage: EMMessage,showTimestamp: Boolean) {
        if(showTimestamp){
            timestamp.visibility = View.VISIBLE
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        }else{
            timestamp.visibility = View.GONE
        }
    }

}