package com.thepascal.greenflag.db

import android.provider.BaseColumns

object UserContract {

    /*********************
     * Database constants *
     *********************/
    object UsersTable: BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAME = "name"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_PHOTO = "photo"
    }
}