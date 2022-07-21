package com.xiongtao.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMClient
import com.xiongtao.im.R
import com.xiongtao.im.adapter.EMCallBackAdapter
import com.xiongtao.im.data.AddFriendItem
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

class AddFriendListItemView(context: Context?, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.view_add_friend_item, this)

    }

    fun bindView(addFriendItem: AddFriendItem) {
        if (addFriendItem.isAdded) {
            add.isEnabled = false
            add.text = "已添加"
        } else {
            add.isEnabled = true
            add.text = "添加"
        }
        user_name.text = addFriendItem.username
        timestamp.text = addFriendItem.timeStamp

        add.setOnClickListener { addFriend(addFriendItem.username) }
    }

    fun addFriend(username: String) {
        EMClient.getInstance().contactManager().aysncAddContact(username, null, object :
            EMCallBackAdapter() {
            override fun onSuccess() {
                context.runOnUiThread { toast("添加成功") }
            }

            override fun onError(p0: Int, p1: String?) {
               context.runOnUiThread { toast("添加失败") }
            }
        })

    }
}