package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.FormValidation.Companion.validateAccountCreationSecondForm
import com.thepascal.greenflag.R
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.repository.UserRepositoryContract
import com.thepascal.greenflag.views.CreateAccountNextContract

class CreateAccountNextPresenter(
    private val createAccountContractNext: CreateAccountNextContract?,
    private val userRepositoryContract: UserRepositoryContract?
) : CreateAccountNextPresenterContract {

    var errors = mutableListOf<FormValidation.FormErrors>()

    override fun doFormValidation(name: String, username: String, dateOfBirth: String, country: String) {
        errors = validateAccountCreationSecondForm(name, username, dateOfBirth, country)

        if (errors.isEmpty()) {
            createAccountContractNext?.onValidationSuccess(name, username, dateOfBirth, country)
        }

        presentFormErrors()
    }

    override fun presentFormErrors() {
        var nameError = 0
        var usernameError = 0
        var dateOfBirthError = 0
        var countryError = 0

        if (errors.contains(FormValidation.FormErrors.NameIsEmpty)) {
            nameError = R.string.form_error_blank_name
        }

        if (errors.contains(FormValidation.FormErrors.UsernameIsEmpty)) {
            usernameError = R.string.form_error_blank_username
        } else if (errors.contains(FormValidation.FormErrors.UsernameIsInvalid)) {
            usernameError = R.string.form_error_invalid_username
        }

        if (errors.contains(FormValidation.FormErrors.DateOfBirthIsMissing)) {
            dateOfBirthError = R.string.form_error_blank_dob
        }

        if (errors.contains(FormValidation.FormErrors.CountryIsMissing)) {
            countryError = R.string.form_error_blank_country
        }

        val createAccountSecondPageErrorEntity = FormValidation.CreateAccountSecondPageErrorEntity(
            nameError, usernameError, dateOfBirthError, countryError
        )
        createAccountContractNext?.onValidationFailure(createAccountSecondPageErrorEntity)
    }

    override fun handleUserInsertion(user: User) {
        userRepositoryContract?.saveUser(user)
    }
}