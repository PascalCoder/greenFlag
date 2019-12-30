package com.thepascal.greenflag.presenters

import com.thepascal.greenflag.FormValidation.LoginFormErrorEntity
import com.thepascal.greenflag.R
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.views.LoginContract
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {

    private lateinit var presenter: LoginPresenter

    @Mock
    lateinit var loginContract: LoginContract

    @Mock
    lateinit var repository: UserRepository

    private val emailBlankError = R.string.login_form_email_empty_error
    private val emailError = R.string.login_form_email_invalid_error
    private val passwordError = R.string.login_form_password_empty_error
    private val authenticationError = R.string.login_auth_error

    private val validEmail = "tester@tester.com"
    private val invalidEmail = "tester@"
    private val emptyEmail = ""
    private val validPassword = "Tester1234"
    private val invalidPassword = ""

    private val validUser = User(
        validEmail,
        "Tester1234",
        "James Rodrigo",
        "JamesRod",
        "3/24/1988",
        "Mexico",
        "Male",
        "",
        0
    )

    @Before
    fun setUp() {
        presenter = LoginPresenter(loginContract, repository, Schedulers.trampoline())
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
    }

    @Test
    fun `test doFormValidation with valid inputs`() {
        //`when`(repository.findUser(validEmail, validPassword)).thenReturn(mutableListOf(validUser))
        `when`(repository.findUserReactively(validEmail, validPassword)).thenReturn(Single.just(mutableListOf(validUser)))
        presenter.doFormValidation(validEmail, validPassword)

        verify(loginContract).onAuthenticationFailure(0)
        verify(loginContract).onLoginSuccess(validUser)
        verify(loginContract).onLoginFailure(LoginFormErrorEntity())
    }

    @Test
    fun `test doFormValidation with valid inputs but no user found`() {
        `when`(repository.findUserReactively(validEmail, validPassword)).thenReturn(Single.just(mutableListOf()))
        presenter.doFormValidation(validEmail, validPassword)

        //Thread.sleep(1000)

        verify(loginContract).onAuthenticationFailure(authenticationError)
        verify(loginContract).onLoginFailure(LoginFormErrorEntity())
    }

    @Test
    fun `test doFormValidation with invalid inputs empty email`() {
        presenter.doFormValidation(emptyEmail, invalidPassword)

        verify(loginContract).onLoginFailure(LoginFormErrorEntity(emailBlankError, passwordError))
    }

    @Test
    fun `test doFormValidation with invalid inputs`() {
        presenter.doFormValidation(invalidEmail, invalidPassword)

        verify(loginContract).onLoginFailure(LoginFormErrorEntity(emailError, passwordError))
    }

    @Test
    fun `test presentFormErrors with valid inputs`() {
        presenter.presentFormErrors()
        verify(loginContract).onLoginFailure(LoginFormErrorEntity())
    }

    /*@Test
    fun `test searchUser if user exists`() {
        presenter.searchUser(validEmail, validPassword)
        verify(repository).findUser(validEmail, validPassword)
    }*/

    /*@Test
    fun `test searchUser if user does not exist`() {
        `when`(repository.findUser(validEmail, validPassword)).thenReturn(mutableListOf())
        presenter.searchUser(validEmail, validPassword)
        verify(repository).findUser(validEmail, validPassword)

        assertEquals(0, repository.findUser(validEmail, validPassword).size)
    }*/
}