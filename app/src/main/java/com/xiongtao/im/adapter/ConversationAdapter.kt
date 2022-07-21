package com.xiongtao.im.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMConversation
import com.xiongtao.im.ui.activity.ChatActivity
import com.xiongtao.im.widget.ConversationItemView
import org.jetbrains.anko.startActivity

class ConversationAdapter(val context: Context, val conversations: MutableList<EMConversation>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ConversationViewHolder(ConversationItemView(context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val conversationItemView = holder.itemView as ConversationItemView
        conversationItemView.bindView(conversations[position])
        conversationItemView.setOnClickListener{context.startActivity<ChatActivity>("userName" to conversations[position].conversationId())}
    }

    override fun getItemCount(): Int = conversations.size

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}