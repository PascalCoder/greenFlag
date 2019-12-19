package com.thepascal.greenflag.router

import android.app.Activity
import android.content.Context
import com.thepascal.greenflag.models.AccountCreationFirstPage
import com.thepascal.greenflag.models.User

interface Router {

    fun goToLoginPage(context: Context)
    fun goToCreateAccountPage(context: Context)
    fun goToCreateAccountSecondPage(context: Context, accountCreationFirstPage: AccountCreationFirstPage)
    fun goToMainPage(context: Context)
    fun pickImageFromGallery(activity: Activity)
    fun goToHomePage(context: Context, user: User)
}