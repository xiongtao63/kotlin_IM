package com.xiongtao.im.presenter

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import com.xiongtao.im.contract.AddFriendContract
import com.xiongtao.im.data.AddFriendItem
import com.xiongtao.im.data.ContactListItem
import com.xiongtao.im.data.dp.IMDatabase
import com.xiongtao.im.widget.ContactItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddFriendPresenter(val view: AddFriendContract.View): AddFriendContract.Presenter {

    var addFriendItems = mutableListOf<AddFriendItem>()
    override fun search(key: String) {
        val query =  BmobQuery<BmobUser>()
        query.addWhereContains("username",key)
        query.addWhereNotEqualTo("username",EMClient.getInstance().currentUser)
        query.findObjects(object : FindListener<BmobUser>() {
            override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                if(p1 == null) {
                    //数据处理
                        doAsync {
                            val allContacts = IMDatabase.instance.getAllContacts()

                            p0?.forEach {
                                var isAdded = false
                                for (contact in allContacts){
                                    if(contact.name == it.username){
                                        isAdded = true
                                    }
                                }
                                val addFriendItem = AddFriendItem(it.username,it.createdAt,isAdded)
                                addFriendItems.add(addFriendItem)


                            }
                            uiThread { view.onSearchSuccess() }
                        }

                }
                else view.onSearchFailed()
            }

        })
    }
}