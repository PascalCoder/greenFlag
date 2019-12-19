package com.thepascal.greenflag

import com.thepascal.greenflag.Constants.CHOOSE_COUNTRY_PROMPT

class FormValidation {

    companion object {
        fun validateLoginForm(email: String, password: String): MutableList<FormErrors> {
            val errorsList = mutableListOf<FormErrors>()

            if (email.isBlank()) {
                errorsList.add(FormErrors.EmailIsEmpty)
            } else if (!isEmailValid(email)) {
                errorsList.add(FormErrors.EmailIsInvalid)
            }

            if (password.isBlank()) {
                errorsList.add(FormErrors.PasswordIsEmpty)
            }

            return errorsList
        }

        fun validateAccountCreationFirstForm(
            email: String,
            password: String,
            passwordRepeat: String
        ): MutableList<FormErrors> {

            val errorsList = mutableListOf<FormErrors>()

            if (!isEmailValid(email)) {
                errorsList.add(FormErrors.EmailIsInvalid)
            }
            if (!isPasswordValid(password)) {
                errorsList.add(FormErrors.PasswordInvalid)
            } else {
                if (!password.equals(passwordRepeat)) {
                    errorsList.add(FormErrors.PasswordsNotMatching)
                }
            }

            return errorsList
        }

        fun validateAccountCreationSecondForm(
            name: String,
            username: String,
            dateOfBirth: String,
            country: String
        ): MutableList<FormErrors> {

            val errorsList = mutableListOf<FormErrors>()

            if (name.isBlank()) {
                errorsList.add(FormErrors.NameIsEmpty)
            }

            if (username.isBlank()) {
                errorsList.add(FormErrors.UsernameIsEmpty)
            } else if (!isUsernameValid(username)) {
                errorsList.add(FormErrors.UsernameIsInvalid)
            } else {

            }

            if (!dateOfBirth.contains('/')) {
                errorsList.add(FormErrors.DateOfBirthIsMissing)
            }

            if (country.equals(CHOOSE_COUNTRY_PROMPT)) {
                errorsList.add(FormErrors.CountryIsMissing)
            }

            return errorsList
        }
    }

    enum class FormErrors {
        EmailAlreadyExists,
        EmailIsEmpty,
        EmailIsInvalid,
        PasswordInvalid,
        PasswordIsEmpty,
        PasswordsNotMatching,
        NameIsEmpty,
        UsernameIsEmpty,
        UsernameIsInvalid,
        DateOfBirthIsMissing,
        CountryIsMissing
    }

    data class LoginFormErrorEntity(
        val emailError: Int = 0,
        val passwordError: Int = 0
    )

    data class CreateAccountFirstPageErrorEntity(
        val emailInvalidError: Int = 0,
        val passwordInvalidError: Int = 0,
        val passwordRepeatError: Int = 0
    )

    data class CreateAccountSecondPageErrorEntity(
        val nameError: Int = 0,
        val usernameError: Int = 0,
        val dateOfBirthError: Int = 0,
        val countryError: Int = 0
    )
}
