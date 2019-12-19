package com.thepascal.greenflag.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.db.UserContract.UsersTable

fun insertUser(context: Context, user: User) {

    val dbHelper = UserDBHelper(context)
    val db: SQLiteDatabase = dbHelper.writableDatabase

    val values = ContentValues().apply {
        put(UsersTable.COLUMN_EMAIL, user.email)
        put(UsersTable.COLUMN_PASSWORD, user.password)
        put(UsersTable.COLUMN_NAME, user.name)
        put(UsersTable.COLUMN_USERNAME, user.username)
        put(UsersTable.COLUMN_DATE_OF_BIRTH, user.dateOfBirth)
        put(UsersTable.COLUMN_COUNTRY, user.country)
        put(UsersTable.COLUMN_ADDRESS, user.address)
        put(UsersTable.COLUMN_PHOTO, user.photo)
    }

    val newRowId = db.insert(UsersTable.TABLE_NAME, null, values)

    Log.i("Inserted", "Row: $newRowId")
}

fun findUser(context: Context, email: String, password: String): MutableList<User> {

    val dbHelper = UserDBHelper(context)
    val db = dbHelper.writableDatabase

    val projection = arrayOf(
        BaseColumns._ID, UsersTable.COLUMN_EMAIL, UsersTable.COLUMN_PASSWORD,
        UsersTable.COLUMN_NAME, UsersTable.COLUMN_USERNAME, UsersTable.COLUMN_DATE_OF_BIRTH,
        UsersTable.COLUMN_COUNTRY, UsersTable.COLUMN_ADDRESS, UsersTable.COLUMN_PHOTO
    )

    val selection = "${UsersTable.COLUMN_EMAIL} = ? " +
            "and ${UsersTable.COLUMN_PASSWORD} = ?"
    val selection2 = "${UsersTable.COLUMN_PASSWORD} = ?"

    val selectionArgs = arrayOf(email, password)

    val setOrder = "${UsersTable.COLUMN_NAME} DESC" //not needed here

    val cursor = db.query(
        UsersTable.TABLE_NAME,  // The table to query
        projection,             // The array of columns to return (pass null to get all)
        selection,              // The columns for the WHERE clause
        selectionArgs,          // The values for the WHERE clause
        null,          // don't group the rows
        null,           // don't filter by row groups
        setOrder               // The sort order
    )

    val userList = mutableListOf<User>()
    with(cursor) {
        while (moveToNext()) {

            val mEmail = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_EMAIL))
            val mPassword = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD))
            val name = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_NAME))
            val username = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME))
            val dateOfBirth = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_DATE_OF_BIRTH))
            val country = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_COUNTRY))
            val gender = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_GENDER))
            val address = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_ADDRESS))
            val photo = cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_PHOTO))

            val user = User(
                mEmail, mPassword, name, username,
                dateOfBirth, country, gender, address, photo
            )

            userList.add(user)
        }
    }
    cursor.close()

    return userList
}

fun findUser(context: Context, email: String) {

    val dbHelper = UserDBHelper(context)
    val db = dbHelper.writableDatabase

    val projection = arrayOf(
        BaseColumns._ID, UsersTable.COLUMN_EMAIL, UsersTable.COLUMN_PASSWORD,
        UsersTable.COLUMN_NAME, UsersTable.COLUMN_USERNAME, UsersTable.COLUMN_DATE_OF_BIRTH,
        UsersTable.COLUMN_COUNTRY, UsersTable.COLUMN_ADDRESS, UsersTable.COLUMN_PHOTO
    )

    val selection1 = "${UsersTable.COLUMN_EMAIL} = ?"

    val selectionArgs = arrayOf(email)

    val setOrder = "${UsersTable.COLUMN_NAME} DESC" //not needed here

    val cursor = db.query(
        UsersTable.TABLE_NAME,
        projection,
        selection1,
        selectionArgs,
        null,
        null,
        setOrder
    )

    cursor.close()
}