package com.example.android.glook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                val signIn : Button = findViewById(R.id.onboard_login_button)
            val signUp : Button = findViewById(R.id.onboard_signup_button)

            signIn.setOnClickListener{
                startActivity(Intent(this,LoginActivity::class.java))
                finish()

            }

            signUp.setOnClickListener{
                startActivity(Intent(this,SignupActivity::class.java))
                finish()

            }}



    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().getCurrentUser()
        if (user != null) {
            // User is signed in

            var i = Intent(this, HomeActivity::class.java)
            // i!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        } else {
            // User is signed out
            Log.d("LoginActivity", "onAuthStateChanged:signed_out")
        }


    }

        }





