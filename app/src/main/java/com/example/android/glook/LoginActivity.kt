package com.example.android.glook

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null
    private var loginCard: CardView? = null


    private var login_email_et: EditText? = null
    private var login_password_et: EditText? = null
    private var sign_in_btn: Button? = null
    private var phone : String? = null
    private var firstName: String? = null
    private var lastName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mAuth = FirebaseAuth.getInstance()

        login_email_et = findViewById(R.id.login_email_et)
        login_password_et= findViewById(R.id.login_password_et)
        sign_in_btn= findViewById(R.id.sign_in_btn)


        sign_in_btn!!.setOnClickListener{logInUser()}
        sign_up_link.setOnClickListener{startActivity(Intent(this, SignupActivity::class.java))
            finish()}

        val intent = intent
        if (intent.hasExtra("email")){
           var email = intent.getStringExtra("email")
            var password = intent.getStringExtra("password")
//             phone = intent.getStringExtra("phone")
             firstName = intent.getStringExtra("firstName")
             lastName = intent.getStringExtra("lastName")

            login_email_et!!.setText(email)
            login_password_et!!.setText(password)


        }else {return}


       //forgot_password_link.setOnClickListener{startActivity(Intent(this, ForgotPasswordActivity::class.java))}
    }


    private fun logInUser() {
        //Show a Progress Dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Checking Fields")
        progressDialog.show()

        //extract the EditText fields into string variables
        var email = login_email_et!!.text.toString()
        var password = login_password_et!!.text.toString()

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            progressDialog.dismiss()
                login_email_et!!.setError("Please enter a valid email")
                login_password_et!!.setError("Please enter your password")
            }else{
            progressDialog.setTitle("Logging In...")
            mAuth!!.signInWithEmailAndPassword(email, password.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDialog.dismiss()
                        val loginIntent = Intent(this,AddBusinessActivity::class.java)
                        // val user = User(firstName, lastName, email, password, phone)


                        loginIntent.putExtra("firstName",firstName)
                       // loginIntent.putExtra("email", email)
                        //loginIntent.putExtra("password",password)
                        loginIntent.putExtra("lastName",lastName)
                        loginIntent.putExtra("phone",phone)
                        startActivity(loginIntent)
                        finish()

                        //Manage error
                    }else // val user = User(firstName, lastName, email, password, phone)

                    //Manage error
                    {
                       // showMessage(loginCard, "Error: ${task.exception?.message}")
                    }
        }
    }
}


//ProgressDialog proDialog = ProgressDialog.show(this, "title", "message");
}
