package com.thepascal.greenflag.views

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.models.User

interface LoginContract {
    fun onLoginSuccess(user: User)
    fun onLoginFailure(loginFormErrorEntity: FormValidation.LoginFormErrorEntity)
    fun onAuthenticationFailure(error: Int)
    fun onAuthenticationFailure(errorMsg: String)
}