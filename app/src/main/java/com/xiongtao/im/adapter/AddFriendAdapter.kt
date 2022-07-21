package com.xiongtao.im.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiongtao.im.data.AddFriendItem
import com.xiongtao.im.widget.AddFriendListItemView

class AddFriendAdapter(val context: Context, val addFriendItems: MutableList<AddFriendItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendListViewHolder(AddFriendListItemView(context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val addFriendListItemView = holder.itemView as AddFriendListItemView
        addFriendListItemView.bindView(addFriendItems[position])
    }

    override fun getItemCount(): Int = addFriendItems.size

    class AddFriendListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}