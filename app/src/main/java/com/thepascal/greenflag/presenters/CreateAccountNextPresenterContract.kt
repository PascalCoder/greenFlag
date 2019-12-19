package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.models.User

interface CreateAccountNextPresenterContract {

    fun doFormValidation(name: String, username: String, dateOfBirth: String, country: String)
    fun presentFormErrors()
    fun handleUserInsertion(user: User)
}