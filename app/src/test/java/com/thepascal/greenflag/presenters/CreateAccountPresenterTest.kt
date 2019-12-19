package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.R
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.views.CreateAccountContract
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateAccountPresenterTest {

    private lateinit var presenter: CreateAccountPresenter

    @Mock
    lateinit var createAccountContract: CreateAccountContract

    @Mock
    lateinit var repository: UserRepository


    private val emailInvalidError = R.string.form_error_email_invalid
    private val emailAlreadyExistError = R.string.form_error_email_already_exists
    private val passwordInvalidError = R.string.form_error_password_invalid
    private val passwordRepeatError = R.string.form_error_passwords_error

    private val validEmail = "tester@tester.com"
    private val invalidEmail = ""
    private val validPassword = "Testing12"
    private val invalidPassword = "Test"
    private val validPasswordRepeat = "Testing12"
    private val invalidPasswordRepeat = "Testing13"

    @Before
    fun setUp() {
        presenter = CreateAccountPresenter(createAccountContract, repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test doFormValidation with valid entries`() {
        presenter.doFormValidation(validEmail, validPassword, validPasswordRepeat)
        verify(repository).doesUserExist(validEmail)
        verify(createAccountContract).onValidationSuccess(validEmail, validPassword)
        verify(createAccountContract).onValidationFailure(FormValidation.CreateAccountFirstPageErrorEntity())
    }

    @Test
    fun `test doFormValidation with email already exist`() {
        Mockito.`when`(repository.doesUserExist(validEmail)).thenReturn(true)
        presenter.doFormValidation(validEmail, validPassword, validPasswordRepeat)
        verify(repository).doesUserExist(validEmail)
        verify(createAccountContract).onValidationFailure(
            FormValidation.CreateAccountFirstPageErrorEntity(
                emailAlreadyExistError
            )
        )
    }

    @Test
    fun `test doFormValidation with invalid email`() {
        presenter.doFormValidation(invalidEmail, validPassword, validPasswordRepeat)
        verify(createAccountContract).onValidationFailure(
            FormValidation.CreateAccountFirstPageErrorEntity(
                emailInvalidError
            )
        )
    }

    @Test
    fun `test doFormValidation with invalid email and password`() {
        presenter.doFormValidation(invalidEmail, invalidPassword, validPasswordRepeat)
        verify(createAccountContract).onValidationFailure(
            FormValidation.CreateAccountFirstPageErrorEntity(
                emailInvalidError,
                passwordInvalidError
            )
        )
    }

    @Test
    fun `test doFormValidation with invalid email and password repeat`() {
        presenter.doFormValidation(invalidEmail, validPassword, invalidPasswordRepeat)
        verify(createAccountContract).onValidationFailure(
            FormValidation.CreateAccountFirstPageErrorEntity(
                emailInvalidError,
                0,
                passwordRepeatError
            )
        )
    }

    @Test
    fun `test presentFormErrors with no error`() {
        presenter.presentFormErrors()
        verify(createAccountContract).onValidationFailure(FormValidation.CreateAccountFirstPageErrorEntity())
    }

    @Test
    fun `test presentFormErrors with email error1`() {
        presenter.errors = mutableListOf(FormValidation.FormErrors.EmailIsInvalid)
        presenter.presentFormErrors()
        verify(createAccountContract).onValidationFailure(
            FormValidation.CreateAccountFirstPageErrorEntity(
                emailInvalidError
            )
        )
    }

    @Test
    fun `test checkIfUserExists`() {
    }
}