package com.xiongtao.im.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.hyphenate.chat.EMClient
import com.xiongtao.im.R
import com.xiongtao.im.data.ContactListItem
import com.xiongtao.im.ui.activity.ChatActivity
import com.xiongtao.im.widget.ContactItem
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ContractAdapter(val context: Context, val contactListItems: MutableList<ContactListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = ContactItem(context)
        return ContractItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contractItem = holder.itemView as ContactItem
        contractItem.bindView(contactListItems[position])
        val username = contactListItems[position].userName
        contractItem.setOnClickListener {
            context.startActivity<ChatActivity>("userName" to username)
        }
        contractItem.setOnLongClickListener {
            deleteUser(username)
            true
        }
    }

    private fun deleteUser(username: String) {
        AlertDialog.Builder(context).setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_title)
            .setNegativeButton(R.string.delete_cancel, null)
            .setPositiveButton(R.string.delete_sure
            ) { dialog, which -> deleteFriend(username) }.show()
    }

    private fun deleteFriend(username: String) {
        EMClient.getInstance().contactManager().aysncDeleteContact(username, object :
            EMCallBackAdapter() {
            override fun onSuccess() {

                context.runOnUiThread { context.toast("删除好友成功") }
            }

            override fun onError(p0: Int, p1: String?) {
                context.runOnUiThread { context.toast("删除好友失败") }
            }

        })

    }

    override fun getItemCount(): Int {
        return contactListItems.size
    }

    class ContractItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}