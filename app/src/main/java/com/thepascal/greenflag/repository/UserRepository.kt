package com.thepascal.greenflag.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.thepascal.greenflag.db.UserContract.UsersTable
import com.thepascal.greenflag.db.UserDBHelper
import com.thepascal.greenflag.models.User
import io.reactivex.Single

class UserRepository(val context: Context): UserRepositoryContract {

    val dbHelper = UserDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    override fun doesUserExist(email: String): Boolean {

        val selection = "${UsersTable.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            UsersTable.TABLE_NAME,  // The table to query
            null,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,          // don't group the rows
            null,           // don't filter by row groups
            null               // The sort order
        )

        val emailList = mutableListOf<String?>()
        with(cursor) {
            while (moveToNext()) {

                val mEmail = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_EMAIL))

                emailList.add(mEmail)
            }
        }
        cursor.close()

        return emailList.size > 0
    }

    override fun doesUserExistReactively(email: String): Single<Boolean> {
        return Single.fromCallable {
            doesUserExist(email)
        }
    }

    fun findUser(email: String, password: String): MutableList<User> {
        /*val dbHelper = UserDBHelper(context)
        val db = dbHelper.writableDatabase*/

        val projection = arrayOf(
            BaseColumns._ID, UsersTable.COLUMN_EMAIL, UsersTable.COLUMN_PASSWORD,
            UsersTable.COLUMN_NAME, UsersTable.COLUMN_USERNAME,
            UsersTable.COLUMN_DATE_OF_BIRTH, UsersTable.COLUMN_COUNTRY,
            UsersTable.COLUMN_ADDRESS, UsersTable.COLUMN_PHOTO
        )

        val selection = "${UsersTable.COLUMN_EMAIL} = ? " +
                "and ${UsersTable.COLUMN_PASSWORD} = ?"

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
                val gender = if(mEmail.contains("tester"))"Male" else cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_GENDER))
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

    override fun findUserReactively(email: String, password: String): Single<MutableList<User>> {
        return Single.fromCallable {
            findUser(email, password)
        }
    }

    override fun saveUser(user: User) {
        //val encryptedPassword = encrypt(user.password)
        val values = ContentValues().apply {
            put(UsersTable.COLUMN_EMAIL, user.email)
            put(UsersTable.COLUMN_PASSWORD, user.password) //encrypt(
            put(UsersTable.COLUMN_NAME, user.name)
            put(UsersTable.COLUMN_USERNAME, user.username)
            put(UsersTable.COLUMN_DATE_OF_BIRTH, user.dateOfBirth)
            put(UsersTable.COLUMN_COUNTRY, user.country)
            put(UsersTable.COLUMN_GENDER, user.gender)
            put(UsersTable.COLUMN_ADDRESS, user.address)
            put(UsersTable.COLUMN_PHOTO, user.photo)
        }

        val newRowId = db.insert(UsersTable.TABLE_NAME, null, values)

        Log.i("Inserted Row", "$newRowId")
    }

    override fun saveUserReactively(user: User): Single<User> {
        return Single.fromCallable {
            saveUser(user)
            user
        }
    }

    override fun updateUser(user: User): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(email: String) {
        val selection = "${UsersTable.COLUMN_EMAIL} LIKE ?"
        val selectionArgs = arrayOf(email)
        val deletedRows = db.delete(UsersTable.TABLE_NAME, selection, selectionArgs)

        Log.i("Deleted Row", "$deletedRows")
    }

    /*private fun encryptPassword(password: String): String {
        val md5 = "MD5"

        return ""
    }*/
}