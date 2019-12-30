package com.thepascal.greenflag.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thepascal.greenflag.*
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.presenters.LoginPresenter
import com.thepascal.greenflag.presenters.LoginPresenterContract
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.router.Router
import com.thepascal.greenflag.router.RouterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract {

    private val router: Router by lazy {
        RouterImpl()
    }

    private lateinit var userRepository: UserRepository
    private lateinit var loginPresenter: LoginPresenterContract

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(applicationContext)
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        userRepository = UserRepository(applicationContext)
        loginPresenter = LoginPresenter(this, userRepository, AndroidSchedulers.mainThread())

        loginButton.setOnClickListener {
            loginPresenter.doFormValidation(
                loginEmail.editText?.text.toString(),
                loginPassword.editText?.text.toString()
            )
        }
    }

    override fun onStop() {
        loginPresenter.unsubscribe()
        super.onStop()
    }

    override fun onDestroy() {
        userRepository.dbHelper.close()
        super.onDestroy()
    }

    override fun onLoginSuccess(user: User) {
        //send user to his/her account
        toast("Logging in success")

        sessionManager.createLoginSession(user.email!!, user.password!!)
        router.goToHomePage(applicationContext, user) //applicationContext requires NEW_TASK flag
        finish()
    }

    override fun onLoginFailure(loginFormErrorEntity: FormValidation.LoginFormErrorEntity) {
        //display error messages
        loginEmail.showError(loginFormErrorEntity.emailError)
        loginPassword.showError(loginFormErrorEntity.passwordError)
    }

    override fun onAuthenticationFailure(error: Int) {
        loginAuthError.displayErrorIfAny(error)
    }

    override fun onAuthenticationFailure(errorMsg: String) {
        loginAuthError.text = errorMsg
    }
}
