package com.example.android.glook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                val signIn : Button = findViewById(R.id.onboard_login_button)
            val signUp : Button = findViewById(R.id.onboard_signup_button)

            signIn.setOnClickListener{
                startActivity(Intent(this,LoginActivity::class.java))

            }

            signUp.setOnClickListener{
                startActivity(Intent(this,SignupActivity::class.java))

            }



    }
}


