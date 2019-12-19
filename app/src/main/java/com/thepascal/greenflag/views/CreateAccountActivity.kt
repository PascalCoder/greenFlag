package com.thepascal.greenflag.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.thepascal.greenflag.FormValidation
import com.thepascal.greenflag.R
import com.thepascal.greenflag.displayErrorIfAny
import com.thepascal.greenflag.models.AccountCreationFirstPage
import com.thepascal.greenflag.presenters.CreateAccountPresenter
import com.thepascal.greenflag.presenters.CreateAccountPresenterContract
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.router.Router
import com.thepascal.greenflag.router.RouterImpl
import com.thepascal.greenflag.setDrawableRight
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity(), CreateAccountContract {

    private val router: Router by lazy {
        RouterImpl()
    }

    private lateinit var userRepository: UserRepository
    private lateinit var createAccountPresenter: CreateAccountPresenterContract

    override fun onValidationSuccess(email: String, password: String) {
        val accountCreationFirstPage = AccountCreationFirstPage(email = email, password = password)
        createAccountEmail.background = ContextCompat.getDrawable(this, R.drawable.validation_outline)
        createAccountEmail.setDrawableRight(R.drawable.tick)
        createAccountPassword.background = ContextCompat.getDrawable(this, R.drawable.validation_outline)
        createAccountPassword.setDrawableRight(R.drawable.tick)
        createAccountPasswordRepeat.background = ContextCompat.getDrawable(this, R.drawable.validation_outline)
        createAccountPasswordRepeat.setDrawableRight(R.drawable.tick)

        router.goToCreateAccountSecondPage(
            this@CreateAccountActivity,
            accountCreationFirstPage
        )
    }

    override fun onValidationFailure(createAccountFirstPageErrorEntity: FormValidation.CreateAccountFirstPageErrorEntity) {
        //textView.showWithError(errorInt: Int)
        createAccountEmailError.displayErrorIfAny(createAccountFirstPageErrorEntity.emailInvalidError)
        createAccountPasswordError.displayErrorIfAny(createAccountFirstPageErrorEntity.passwordInvalidError)
        createAccountPasswordRepeatError.displayErrorIfAny(createAccountFirstPageErrorEntity.passwordRepeatError)
    }

    override fun displayErrors(error: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        userRepository = UserRepository(applicationContext)
        createAccountPresenter = CreateAccountPresenter(this, userRepository)

        createAccountBackArrow.setOnClickListener {
            finish()
        }

        createAccountNextButton.setOnClickListener {

            createAccountPresenter.doFormValidation(
                createAccountEmail.editText?.text.toString(),
                createAccountPassword.editText?.text.toString(),
                createAccountPasswordRepeat.editText?.text.toString()
            )
        }
    }

    override fun onDestroy() {
        userRepository.dbHelper.close()
        super.onDestroy()
    }
}
