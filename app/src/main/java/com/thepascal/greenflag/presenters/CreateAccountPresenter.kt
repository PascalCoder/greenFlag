package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.FormValidation.Companion.validateAccountCreationFirstForm
import com.thepascal.greenflag.R
import com.thepascal.greenflag.repository.UserRepositoryContract
import com.thepascal.greenflag.views.CreateAccountContract

class CreateAccountPresenter(
    private val createAccountContract: CreateAccountContract?,
    private val userRepositoryContract: UserRepositoryContract?
) : CreateAccountPresenterContract {

    var errors = mutableListOf<FormValidation.FormErrors>()

    override fun doFormValidation(email: String, password: String, passwordRepeat: String) {
        errors = validateAccountCreationFirstForm(email, password, passwordRepeat)
        if (errors.isEmpty()) {
            //check if user exists
            if (userRepositoryContract?.doesUserExist(email)!!) {
                errors.add(FormValidation.FormErrors.EmailAlreadyExists)
            } else {
                createAccountContract?.onValidationSuccess(email, password)
            }

            /*val disposableSingleObserver: DisposableSingleObserver<Boolean>? =
                userRepositoryContract?.doesUserExistReactively(email)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(mainScheduler)
                    ?.subscribeWith(object : DisposableSingleObserver<Boolean>() {
                        override fun onSuccess(t: Boolean) {
                            if (t) {
                                errors.add(FormValidation.FormErrors.EmailAlreadyExists)
                            } else {
                                createAccountContract?.onValidationSuccess(email, password)
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.message?.let {
                                //createAccountContract?.onValidationError(it)
                            }
                        }

                    })

            disposableSingleObserver?.let {
                compositeDisposable.add(disposableSingleObserver)
            }*/
        }
        presentFormErrors()

    }

    override fun presentFormErrors() {
        var emailInvalidError = 0
        var passwordInvalidError = 0
        var passwordRepeatError = 0

        if (errors.contains(FormValidation.FormErrors.EmailIsInvalid)) {
            emailInvalidError = R.string.form_error_email_invalid
        } else if (errors.contains(FormValidation.FormErrors.EmailAlreadyExists)) {
            emailInvalidError = R.string.form_error_email_already_exists
        }

        if (errors.contains(FormValidation.FormErrors.PasswordInvalid)) {
            passwordInvalidError = R.string.form_error_password_invalid
        } else {
            if (errors.contains(FormValidation.FormErrors.PasswordsNotMatching)) {
                passwordRepeatError = R.string.form_error_passwords_error
            }
        }

        val createAccountFirstPageErrorEntity = FormValidation.CreateAccountFirstPageErrorEntity(
            emailInvalidError, passwordInvalidError, passwordRepeatError
        )

        createAccountContract?.onValidationFailure(createAccountFirstPageErrorEntity)
    }

    /*override fun checkIfUserExists(email: String): Boolean {
        var userExists = false
        userRepositoryContract?.let {
            userExists = userRepositoryContract.doesUserExist(email)
        }
        return userExists
    }*/
}