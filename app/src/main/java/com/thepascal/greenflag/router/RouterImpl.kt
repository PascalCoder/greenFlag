package com.thepascal.greenflag.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import com.thepascal.greenflag.Constants
import com.thepascal.greenflag.Constants.FIRST_PAGE_DATA
import com.thepascal.greenflag.Constants.USER_INFO
import com.thepascal.greenflag.models.AccountCreationFirstPage
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.views.*

class RouterImpl : Router {

    override fun goToLoginPage(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun goToCreateAccountPage(context: Context) {
        context.startActivity(Intent(context, CreateAccountActivity::class.java))
    }

    override fun goToCreateAccountSecondPage(
        context: Context,
        accountCreationFirstPage: AccountCreationFirstPage
    ) {
        context.startActivity(Intent(context, CreateAccountNextActivity::class.java).apply {
            this.putExtra(FIRST_PAGE_DATA, accountCreationFirstPage)
        })
    }

    override fun goToMainPage(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    override fun pickImageFromGallery(activity: Activity) {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(activity, intent, Constants.RESULT_LOAD_IMAGE, null)
    }

    override fun goToHomePage(context: Context, user: User) {
        context.startActivity(Intent(context, HomeActivity::class.java).apply {
            this.putExtra(USER_INFO, user)
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}