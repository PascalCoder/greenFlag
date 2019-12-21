package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.FormValidation.Companion.validateLoginForm
import com.thepascal.greenflag.R
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.repository.UserRepositoryContract
import com.thepascal.greenflag.views.LoginContract

class LoginPresenter(
    private val loginContract: LoginContract?,
    private val userRepositoryContract: UserRepositoryContract?
) : LoginPresenterContract {

    var errors = mutableListOf<FormValidation.FormErrors>()

    override fun doFormValidation(email: String, password: String) {
        errors = validateLoginForm(email, password)

        if (errors.isEmpty()) {
            val userList: MutableList<User>? = searchUser(email, password)
            if (userList != null && userList.isNotEmpty()) {
                loginContract?.onAuthenticationFailure(0)
                loginContract?.onLoginSuccess(userList[0])
            } else {
                loginContract?.onAuthenticationFailure(R.string.login_auth_error)
            }

        }

        presentFormErrors()
    }

    override fun presentFormErrors() {
        var emailError = 0
        var passwordError = 0

        if (errors.contains(FormValidation.FormErrors.EmailIsEmpty)) {
            emailError = R.string.login_form_email_empty_error
        } else if (errors.contains(FormValidation.FormErrors.EmailIsInvalid)) {
            emailError = R.string.login_form_email_invalid_error
        }

        if (errors.contains(FormValidation.FormErrors.PasswordIsEmpty)) {
            passwordError = R.string.login_form_password_empty_error
        }

        val loginFormErrorEntity = FormValidation.LoginFormErrorEntity(emailError, passwordError)

        loginContract?.onLoginFailure(loginFormErrorEntity)
    }

    override fun searchUser(email: String, password: String): MutableList<User>? {
        return userRepositoryContract?.findUser(email, password)
    }
}