package com.xiongtao.im.data.dp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.xiongtao.im.app.IMApplication
import org.jetbrains.anko.db.*

class DatabaseHelper(
    ctx: Context = IMApplication.instance,
) : ManagedSQLiteOpenHelper(ctx, NAME, null, VERSION) {
    companion object{
        val NAME = "im.db"
        val VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(ContactTable.NAME, true,ContactTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        ContactTable.CONTACT to  TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ContactTable.NAME,true)
        onCreate(db)
    }
}