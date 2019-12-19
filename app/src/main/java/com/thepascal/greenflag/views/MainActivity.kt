package com.thepascal.greenflag.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thepascal.greenflag.R
import com.thepascal.greenflag.router.Router
import com.thepascal.greenflag.router.RouterImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val router: Router by lazy {
        RouterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainButton.setOnClickListener {
            router.goToCreateAccountPage(this@MainActivity)
        }

        mainLogin.setOnClickListener {
            router.goToLoginPage(this)
        }
    }
}
