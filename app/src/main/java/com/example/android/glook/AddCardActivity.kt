package com.example.android.glook

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_add_service.*
import org.jetbrains.anko.toast

class AddCardActivity : AppCompatActivity() {

    private var serviceName1 : String? = null
    private var serviceCost1 : String? = null
    //    private var serviceName2 : String? = null
//    private var serviceCost2 : String? = null
    private var service: Service? = null

    private var mDatabase: DatabaseReference? = null
    var currentFirebaseUser: FirebaseUser? = null
    var userId : String? =null

    private var database: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)


        database = FirebaseDatabase.getInstance().reference


        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        userId = currentFirebaseUser!!.uid

        submit_card_btn.setOnClickListener{checkEditTextFields()}
    }

    private fun checkEditTextFields() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Processing")
        progressDialog.show()

        var accountName = account_name_et.text.toString()
        var accountNumber = account_number_et.text.toString()
        var bankName = bank_name_et.text.toString()

        if(TextUtils.isEmpty(accountName) || TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(bankName)){
            progressDialog.dismiss()
            account_name_et.error = "Please enter your account name"
            account_number_et.error = "Please enter your account number"
            bank_name_et.error = "Please enter your bank name"

        }else{



            val cardPoko = CardPoko(accountName, accountNumber, bankName)
            database!!.child("vendor_cards").child(userId!!).setValue(cardPoko).
                addOnSuccessListener {
                    toast("Upload Successfull")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener{exception ->
                    toast(exception.message!!)

                }

        }





    }

}
