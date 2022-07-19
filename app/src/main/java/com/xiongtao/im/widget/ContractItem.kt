package com.xiongtao.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.xiongtao.im.R
import com.xiongtao.im.data.ContactListItem
import kotlinx.android.synthetic.main.contact_item.view.*
import java.util.zip.Inflater

class ContractItem(context: Context, attrs: AttributeSet? = null):
    RelativeLayout(context, attrs) {
    fun bindView(contactListItem: ContactListItem) {
        if(contactListItem.showFirst){
            first_char.visibility = View.VISIBLE
            first_char.text = contactListItem.firstLetter.toString()
        }else first_char.visibility = View.GONE
        userName.text = contactListItem.userName
    }

    init {
            LayoutInflater.from(context).inflate(R.layout.contact_item,this,true)
        }

}