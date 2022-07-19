package com.xiongtao.im.presenter

import com.xiongtao.im.contract.ContactsContract
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.xiongtao.im.data.ContactListItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ContactsPresenter(val view:ContactsContract.View): ContactsContract.Presenter {
    val contactListItems = mutableListOf<ContactListItem>()
    override fun loadData() {
        contactListItems.clear()
        doAsync {
            try {
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                if(usernames == null || usernames.size == 0){
                    for (i in 0..5) {
                        usernames.add("abc")
                    }
                    for (i in 0..10) {
                        usernames.add("bcd")
                    }
                }
                usernames.sortBy { it[0] }
                usernames.forEachIndexed { index, s ->
                    val isShowFirst = index==0 || s[0] != usernames[index-1][0]
                    val contactListItem = ContactListItem(s,s[0].toUpperCase(),isShowFirst)
                    contactListItems.add(contactListItem)
                }
                uiThread { view.onLoadSuccess() }
            }catch (e: HyphenateException){
                uiThread { view.onLoadFailed() }
            }

        }

    }
}