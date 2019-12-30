package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.FormValidation.Companion.validateLoginForm
import com.thepascal.greenflag.R
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.repository.UserRepositoryContract
import com.thepascal.greenflag.views.LoginContract
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginPresenter(
    private val loginContract: LoginContract?,
    private val userRepositoryContract: UserRepositoryContract?,
    private val mainScheduler: Scheduler
) : LoginPresenterContract {

    private var errors = mutableListOf<FormValidation.FormErrors>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun doFormValidation(email: String, password: String) {
        errors = validateLoginForm(email, password)

        if (errors.isEmpty()) {
            /*userRepositoryContract?.findUserReactively(email, password)
                //?.subscribeOn(Schedulers.io())
                ?.subscribe(Consumer {
                    if (it.isEmpty()) {
                        loginContract?.onAuthenticationFailure(R.string.login_auth_error)
                    } else {
                        loginContract?.onAuthenticationFailure(0)
                        loginContract?.onLoginSuccess(it[0])
                    }
                }, Consumer {
                    loginContract?.onAuthenticationFailure(it.message!!)
                })//?.dispose()*/

            val disposableSingleObserver: DisposableSingleObserver<MutableList<User>>? =
                userRepositoryContract?.findUserReactively(email, password)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(mainScheduler)
                    ?.subscribeWith(object : DisposableSingleObserver<MutableList<User>>() {
                        override fun onSuccess(t: MutableList<User>) {
                            if (t.isEmpty()) {
                                loginContract?.onAuthenticationFailure(R.string.login_auth_error)
                            } else {
                                loginContract?.onAuthenticationFailure(0)
                                loginContract?.onLoginSuccess(t[0])
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.message?.let {
                                loginContract?.onAuthenticationFailure(it)
                            }
                        }
                    })

            disposableSingleObserver?.let {
                compositeDisposable.add(disposableSingleObserver)
            }
        }

        presentFormErrors()
    }

    override fun unsubscribe() { //will need to be called in the onStop or onDestroy of the Activity
        compositeDisposable.clear()
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
}