package com.thepascal.greenflag.presenters

interface CreateAccountPresenterContract {

    fun doFormValidation(email: String, password: String, passwordRepeat: String)
    fun presentFormErrors()
    fun checkIfUserExists(email: String): Boolean
}