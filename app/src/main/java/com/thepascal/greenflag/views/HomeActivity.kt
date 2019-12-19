package com.thepascal.greenflag.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.thepascal.greenflag.Constants
import com.thepascal.greenflag.R
import com.thepascal.greenflag.SessionManager
import com.thepascal.greenflag.models.User
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeContract {

    private lateinit var sessionManager: SessionManager
    //private lateinit var loginPresenter: LoginPresenterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sessionManager = SessionManager(applicationContext)
        //loginPresenter = LoginPresenter(this, UserRepository(applicationContext))


        sessionManager.checkLogin()

        //val userDetails = sessionManager.getUserDetails()

        /*val email = userDetails[KEY_EMAIL]
        val password = userDetails[KEY_PASSWORD]*/
        val user: User? = intent.getParcelableExtra(Constants.USER_INFO)

        if (user == null) {
            sessionManager.logoutUser()
            finish()
        }

        displayUserInfo(user)

        homeLogout.setOnClickListener {
            sessionManager.logoutUser()
            finish()
        }
    }

    private fun displayUserInfo(user: User?) {
        homeHeader.text = String.format(resources.getString(R.string.home_header_tag), user?.name)
        homeEmailTag.text = String.format(resources.getString(R.string.home_email_tag), user?.email)
        homeUsernameTag.text = String.format(resources.getString(R.string.home_username_tag), user?.username)
        homeBirthDateTag.text = String.format(resources.getString(R.string.home_date_of_birth_tag), user?.dateOfBirth)
        homeCountryTag.text = String.format(resources.getString(R.string.home_country_tag), user?.country)
        homeGenderTag.text = String.format(resources.getString(R.string.home_gender_tag), user?.gender)
        homeAddressTag.text = String.format(resources.getString(R.string.home_address_tag), user?.address)
        user?.let {
            homePhoto.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.img_holder))
        }
    }
}
