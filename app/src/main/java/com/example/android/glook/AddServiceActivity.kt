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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_business.*
import kotlinx.android.synthetic.main.activity_add_service.*
import org.jetbrains.anko.toast

class AddServiceActivity : AppCompatActivity() {

    private var serviceName1 : String? = null
    private var serviceCost1 : String? = null
//    private var serviceName2 : String? = null
//    private var serviceCost2 : String? = null
    private var service: Service? = null

    private var mDatabase: DatabaseReference? = null
    var currentFirebaseUser: FirebaseUser? = null
    var userId : String? =null

    private var database: DatabaseReference? = null
// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        database = FirebaseDatabase.getInstance().reference


        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        userId = currentFirebaseUser!!.uid

        submit_service_btn.setOnClickListener{checkEditTextFields()}
    }

    private fun checkEditTextFields() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Validating Fields")
        progressDialog.show()

        serviceName1 = service_name1.text.toString()
        serviceCost1 = service_cost1.text.toString()
//        serviceName2 = service_name2.text.toString()
//        serviceCost2 = service_cost2.text.toString()

        if(TextUtils.isEmpty(serviceName1) || TextUtils.isEmpty(serviceCost1)){
            service_name1.error = "Please enter a service name"
            service_cost1.error = "Please enter an amount for your service"
            progressDialog.dismiss()
        }else{



                 service = Service(serviceName1!!, serviceCost1!!, userId!!)
            database!!.child("services").child(userId!!).setValue(service).
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
