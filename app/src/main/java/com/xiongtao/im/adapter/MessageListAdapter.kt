package com.xiongtao.im.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils
import com.xiongtao.im.widget.ReceiveMessageItemView
import com.xiongtao.im.widget.SendMessageItemView

class MessageListAdapter(val context: Context, val messages: MutableList<EMMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        val ITEM_TYPE_SEND_MESSAGE = 0
        val ITEM_TYPE_RECEIVE_MESSAGE = 1
    }
    override fun getItemViewType(position: Int): Int {
        return if(messages[position].direct() == EMMessage.Direct.SEND){
            ITEM_TYPE_SEND_MESSAGE
        } else {
            ITEM_TYPE_RECEIVE_MESSAGE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_TYPE_SEND_MESSAGE){
            return SendMessageItemHolder(SendMessageItemView(context))
        }else{
            return ReceiveMessageItemHolder(ReceiveMessageItemView(context))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val showTimestamp = isShowTimestamp(position)
        if(getItemViewType(position) == ITEM_TYPE_SEND_MESSAGE){
            val sendMessageItemView = holder.itemView as SendMessageItemView
            sendMessageItemView.bindView(messages[position],showTimestamp)
        }else{
            val receiveMessageItemView = holder.itemView as ReceiveMessageItemView
            receiveMessageItemView.bindView(messages[position],showTimestamp)
        }
    }

    private fun isShowTimestamp(position: Int): Boolean {
        var showTimestamp = true
        if(position > 0){
            showTimestamp = !DateUtils.isCloseEnough(messages[position].msgTime, messages[position - 1].msgTime)
        }
        return showTimestamp
    }

    override fun getItemCount(): Int = messages.size
    class SendMessageItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
    class ReceiveMessageItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}