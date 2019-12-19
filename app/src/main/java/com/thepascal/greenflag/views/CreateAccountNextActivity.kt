package com.thepascal.greenflag.views

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.thepascal.greenflag.*
import com.thepascal.greenflag.Constants.RESULT_LOAD_IMAGE
import com.thepascal.greenflag.models.AccountCreationFirstPage
import com.thepascal.greenflag.models.User
import com.thepascal.greenflag.presenters.CreateAccountNextPresenter
import com.thepascal.greenflag.presenters.CreateAccountNextPresenterContract
import com.thepascal.greenflag.repository.UserRepository
import com.thepascal.greenflag.router.Router
import com.thepascal.greenflag.router.RouterImpl
import kotlinx.android.synthetic.main.activity_create_account_next.*

class CreateAccountNextActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, CreateAccountNextContract {

    private var accountCreationFirstPage: AccountCreationFirstPage? = null
    private var gender: String = "Male"

    private val router: Router by lazy {
        RouterImpl()
    }

    private lateinit var userRepository: UserRepository
    private lateinit var createAccountNextPresenter: CreateAccountNextPresenterContract


    override fun onValidationSuccess(
        name: String, username: String,
        dateOfBirth: String, country: String
    ) {
        //Save data to the local database
        val user = User(
            email = accountCreationFirstPage?.email,
            password = accountCreationFirstPage?.password, name = name,
            username = username, dateOfBirth = dateOfBirth, country = country,
            gender = gender, address = createAccountNextAddress.editText?.text.toString(),
            photo = createAccountNextPicture.imageAlpha
        )/*.apply {
            insertUser(this@CreateAccountNextActivity, this)
        }*/
        createAccountNextPresenter.handleUserInsertion(user)

        Log.i("User", "$user")

        //Navigate to homepage
        router.goToMainPage(this)
    }

    override fun onValidationFailure(
        createAccountSecondPageErrorEntity: FormValidation.CreateAccountSecondPageErrorEntity
    ) {
        //display errors
        createAccountNextName.showError(createAccountSecondPageErrorEntity.nameError)
        createAccountNextUsername.showError(createAccountSecondPageErrorEntity.usernameError)
        createAccountNextBirthDateError.displayErrorIfAny(createAccountSecondPageErrorEntity.dateOfBirthError)
        createAccountNextCountryError.displayErrorIfAny(createAccountSecondPageErrorEntity.countryError)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = "${month + 1}/$dayOfMonth/$year"
        createAccountNextBirthDate.text = date
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account_next)

        userRepository = UserRepository(applicationContext)
        createAccountNextPresenter = CreateAccountNextPresenter(this, userRepository)

        val mAccountCreationFirstPage = intent.getParcelableExtra(Constants.FIRST_PAGE_DATA) as AccountCreationFirstPage
        toast(mAccountCreationFirstPage.email)

        accountCreationFirstPage = mAccountCreationFirstPage

        createAccountNextDatePickerButton.setOnClickListener {
            showDatePickerDialog(this, this)
        }

        val adapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(
                this,
                R.array.countries,
                android.R.layout.simple_spinner_item
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        createAccountNextCountrySpinner.adapter = adapter
        createAccountNextCountrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val country = parent?.getItemAtPosition(position).toString()
                toast("Country: $country")
            }

        }

        createAccountNextChangePhotoButton.setOnClickListener {
            router.pickImageFromGallery(this)
        }

        createAccountNextSaveButton.setOnClickListener {
            toast("Button clicked")
            createAccountNextPresenter.doFormValidation(
                name = createAccountNextName.editText?.text.toString(),
                username = createAccountNextUsername.editText?.text.toString(),
                dateOfBirth = createAccountNextBirthDate.text.toString(),
                country = createAccountNextCountrySpinner.selectedItem.toString()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumn: Array<String> = arrayOf(MediaStore.Images.Media.DATA)

            val cursor: Cursor? = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor?.moveToFirst()

            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath: String? = cursor?.getString(columnIndex!!)
            cursor?.close()

            createAccountNextPicture.setImageResource(0)
            createAccountNextPicture.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }

    override fun onDestroy() {
        userRepository.dbHelper.close()
        super.onDestroy()
    }


    fun checkRadioButton(view: View) {
        val radioButtonId = createAccountNextGender.checkedRadioButtonId

        val radioButton = findViewById<RadioButton>(radioButtonId)
        gender = radioButton.text.toString()

        toast("Gender: ${radioButton.text}")
    }

}
