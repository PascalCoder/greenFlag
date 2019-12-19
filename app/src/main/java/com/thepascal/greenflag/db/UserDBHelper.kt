package com.thepascal.greenflag.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.thepascal.greenflag.db.UserContract.UsersTable


class UserDBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "Users.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${UsersTable.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${UsersTable.COLUMN_EMAIL} TEXT," +
                    "${UsersTable.COLUMN_PASSWORD} TEXT," +
                    "${UsersTable.COLUMN_NAME} TEXT," +
                    "${UsersTable.COLUMN_USERNAME} TEXT," +
                    "${UsersTable.COLUMN_DATE_OF_BIRTH} TEXT," +
                    "${UsersTable.COLUMN_COUNTRY} TEXT," +
                    "${UsersTable.COLUMN_GENDER} TEXT," +
                    "${UsersTable.COLUMN_ADDRESS} TEXT," +
                    "${UsersTable.COLUMN_PHOTO} INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UsersTable.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

}