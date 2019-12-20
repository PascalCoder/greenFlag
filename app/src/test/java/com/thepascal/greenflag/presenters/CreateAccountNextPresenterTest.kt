package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.FormValidation.FormErrors.*
import com.thepascal.greenflag.R
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.views.CreateAccountNextContract
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateAccountNextPresenterTest {

    private lateinit var presenter: CreateAccountNextPresenter

    @Mock
    lateinit var createAccountNextContract: CreateAccountNextContract

    @Mock
    lateinit var repository: UserRepository

    private val validName = "Pascal Arvee"
    private val invalidName = ""
    private val validUsername = "Pascal87"
    private val invalidUsername = "Pal"
    private val blankUsername = ""
    private val validDateOfBirth = "1/21/1987"
    private val invalidDateOfBirth = "Choose Birth Date"
    private val validCountry = "Ivory Coast"
    private val invalidCountry = "Choose Country"
    private val validUser = User(
        "tester@tester.com",
        "Tester1234",
        validName,
        validUsername,
        validDateOfBirth,
        validCountry,
        "Male",
        "",
        0
    )

    private val nameError = R.string.form_error_blank_name
    private val usernameEmptyError = R.string.form_error_blank_username
    private val usernameInvalidError = R.string.form_error_invalid_username
    private val dateOfBirthError = R.string.form_error_blank_dob
    private val countryError = R.string.form_error_blank_country

    @Before
    fun setUp() {
        presenter = CreateAccountNextPresenter(createAccountNextContract, repository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test doFormValidation with valid entries`() {
        presenter.doFormValidation(validName, validUsername, validDateOfBirth, validCountry)
        verify(
            createAccountNextContract
        ).onValidationSuccess(
            validName,
            validUsername,
            validDateOfBirth,
            validCountry
        )

        verify(createAccountNextContract).onValidationFailure(
            FormValidation.CreateAccountSecondPageErrorEntity()
        )
    }

    @Test
    fun `test doFormValidation with invalid entries`() {
        presenter.doFormValidation(invalidName, invalidUsername, invalidDateOfBirth, invalidCountry)

        verify(createAccountNextContract).onValidationFailure(
            FormValidation.CreateAccountSecondPageErrorEntity(
                nameError,
                usernameInvalidError,
                dateOfBirthError,
                countryError
            )
        )
    }

    @Test
    fun `test doFormValidation with invalid entries empty username`() {
        presenter.doFormValidation(invalidName, blankUsername, invalidDateOfBirth, invalidCountry)

        verify(createAccountNextContract).onValidationFailure(
            FormValidation.CreateAccountSecondPageErrorEntity(
                nameError,
                usernameEmptyError,
                dateOfBirthError,
                countryError
            )
        )
    }

    @Test
    fun `test presentFormErrors with valid entries`() {
        presenter.presentFormErrors()
        verify(createAccountNextContract).onValidationFailure(FormValidation.CreateAccountSecondPageErrorEntity())
    }

    @Test
    fun `test presentFormErrors with invalid entries`() {
        presenter.errors = mutableListOf(
            NameIsEmpty, UsernameIsEmpty,
            DateOfBirthIsMissing, CountryIsMissing
        )
        presenter.presentFormErrors()
        verify(createAccountNextContract).onValidationFailure(
            FormValidation.CreateAccountSecondPageErrorEntity(
                nameError,
                usernameEmptyError,
                dateOfBirthError,
                countryError
            )
        )
    }

    @Test
    fun `test handleUserInsertion`() {
        presenter.handleUserInsertion(validUser)
        verify(repository).saveUser(validUser)
    }
}