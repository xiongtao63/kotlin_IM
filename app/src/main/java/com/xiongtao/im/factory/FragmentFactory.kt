package com.xiongtao.im.factory

import androidx.fragment.app.Fragment
import com.xiongtao.im.R
import com.xiongtao.im.ui.fragment.ContactsFragment
import com.xiongtao.im.ui.fragment.ConversationFragment
import com.xiongtao.im.ui.fragment.DynamicFragment

class FragmentFactory private constructor() {
    companion object {
        val instances = FragmentFactory()
    }

    fun getFragment(id: Int): Fragment? {
        when (id) {
            R.id.tab_conversation -> ConversationFragment()
            R.id.tab_contacts -> ContactsFragment()
            R.id.tab_dynamic -> DynamicFragment()
        }
        return null

    }
}