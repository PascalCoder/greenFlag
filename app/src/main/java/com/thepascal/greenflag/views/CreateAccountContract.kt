package com.thepascal.greenflag.views

import com.thepascal.greenflag.FormValidation

interface CreateAccountContract {
    fun onValidationSuccess(email: String, password: String)
    fun onValidationFailure(createAccountFirstPageErrorEntity: FormValidation.CreateAccountFirstPageErrorEntity)
    fun displayErrors(error: String)
}