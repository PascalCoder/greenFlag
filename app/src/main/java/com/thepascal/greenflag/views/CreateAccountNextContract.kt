package com.thepascal.greenflag.views

import com.thepascal.greenflag.FormValidation

interface CreateAccountNextContract {

    fun onValidationSuccess(
        name: String, username: String,
        dateOfBirth: String, country: String
    )

    fun onValidationFailure(
        createAccountSecondPageErrorEntity: FormValidation.CreateAccountSecondPageErrorEntity
    )
}