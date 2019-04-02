package com.example.android.glook

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class SignupActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null


    private var signUp : Button? = null
    private var firstName_et: TextInputEditText? = null
    private  var lastName_et: TextInputEditText? = null
    private var email_et: EditText? = null
    private var phone_et: TextInputEditText? = null
    private var password_et: TextInputEditText? = null
//    private var firstName = String
//    private var lastName = String
////    private var email = String
////    private var password = String
//    private var phone = String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth= FirebaseAuth.getInstance()

        signUp  = findViewById(R.id.submit_shop_btn)
        firstName_et = findViewById(R.id.signUp_fistName_et)
        lastName_et= findViewById(R.id.signUp_lastName_et)
        email_et = findViewById(R.id.signUp_email_et)
        // phone_et= findViewById(R.id.signUp_phone_et)
        password_et = findViewById(R.id.signUp_password_et)

        sign_up_btn.setOnClickListener{validateEditText()}
        sign_in_link.setOnClickListener{startActivity(Intent(this, LoginActivity::class.java))
            finish()}

    }

    private fun validateEditText(){


        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Checking Fields")
        progressDialog.show()

        val  email = email_et!!.text.toString()
          val  password  = password_et!!.text.toString()
            val firstName = firstName_et!!.text.toString()
            val lastName = lastName_et!!.text.toString()
            //val phone = phone_et!!.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)){
            // mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {  })

            progressDialog.setTitle("Registering User")
            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDialog.dismiss()
                        val loginIntent = Intent(this,LoginActivity::class.java)
                       // val user = User(firstName, lastName, email, password, phone)

                        loginIntent.putExtra("email",email)
                        loginIntent.putExtra("password",password)
                        loginIntent.putExtra("firstName",firstName)
                        loginIntent.putExtra("lastName",lastName)
              //          loginIntent.putExtra("phone",phone)
                        startActivity(loginIntent)
                        finish()

                           //Manage error
                    }else // val user = User(firstName, lastName, email, password, phone)

                        //Manage error
                    {
                        progressDialog.dismiss()
                       toast("Error: ${task.exception?.message}")
                    }
                }

            } else{
            firstName_et!!.setError("Enter a valid name")
            lastName_et!!.setError("Enter a valid email")
            email_et!!.setError("Enter a valid email")
            password_et!!.setError("Enter a strong password")
        }

    }

}
