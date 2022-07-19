package com.xiongtao.im.adapter

import com.hyphenate.EMContactListener

open class EMContactListenerAdapter: EMContactListener {
    // 对方同意了好友请求。
    override fun onFriendRequestAccepted(username: String?) {}

    // 对方拒绝了好友请求。
    override fun onFriendRequestDeclined(username: String?) {}

    // 接收到好友请求。
    override fun onContactInvited(username: String?, reason: String?) {}

    // 联系人被删除。
    override fun onContactDeleted(username: String?) {}

    // 联系人已添加。
    override fun onContactAdded(username: String?) {}
}