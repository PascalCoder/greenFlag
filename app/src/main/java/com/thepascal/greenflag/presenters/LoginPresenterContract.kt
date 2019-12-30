package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.models.User

interface LoginPresenterContract {

    fun doFormValidation(email: String, password: String)
    fun presentFormErrors()
    fun unsubscribe()
    //fun searchUser(email: String, password: String): MutableList<User>?
}