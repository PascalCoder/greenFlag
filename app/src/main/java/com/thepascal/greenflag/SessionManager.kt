package com.thepascal.greenflag

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import com.thepascal.greenflag.Constants.IS_USER_LOGGED_IN
import com.thepascal.greenflag.Constants.KEY_EMAIL
import com.thepascal.greenflag.Constants.KEY_PASSWORD
import com.thepascal.greenflag.Constants.PREF_NAME
import com.thepascal.greenflag.views.LoginActivity

class SessionManager(val context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()


    fun createLoginSession(email: String, password: String) {
        editor.putBoolean(IS_USER_LOGGED_IN, true)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.commit()
    }

    fun checkLogin() {
        if (!this.isLoggedIn()) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    fun getUserDetails(): HashMap<String, String?> {
        val userDetails = HashMap<String, String?>()
        /*userDetails.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null))
        userDetails.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null))*/

        userDetails[KEY_EMAIL] = pref.getString(KEY_EMAIL, null)
        userDetails[KEY_PASSWORD] = pref.getString(KEY_PASSWORD, null)

        return userDetails
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()

        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_USER_LOGGED_IN, false)
    }
}