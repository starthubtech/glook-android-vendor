package com.example.android.glook

import android.R
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_business.*
import android.content.Intent
import android.provider.MediaStore
import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import android.widget.Toast
import android.app.ProgressDialog
import android.text.TextUtils
import android.webkit.MimeTypeMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class AddBusinessActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 234

    private var shopName: String?= null
    private var shopAddress: String? = null
    private var shopBvn: String? = null


    private var upload : Upload? = null
    private var image_url: String? = null


    //uri to store file
    private var filePath: Uri? = null

    private var downloadUrl: String? = null

    //firebase objects
    private var storageReference: StorageReference? = null
    private var mDatabase: DatabaseReference? = null
     var currentFirebaseUser: FirebaseUser? = null
    var userId : String? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.glook.R.layout.activity_add_business)

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("business_image")
        business_image.setOnClickListener{showFileChooser()}
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser()
        userId = currentFirebaseUser!!.uid

        submit_shop_btn.setOnClickListener{
            checkEditTextFields()
    }


    }

    private fun checkEditTextFields() {
         shopName = shop_name_et!!.text.toString()
         shopAddress = shop_address_et!!.text.toString()
         shopBvn = bvn_et!!.text.toString()

        if(TextUtils.isEmpty(shopName) || TextUtils.isEmpty(shopAddress) || TextUtils.isEmpty(shopBvn)){
            if(TextUtils.isEmpty(shopName)){shop_name_et.error = "Please Enter a name for your Shop"}
            else if(TextUtils.isEmpty(shopAddress)){shop_address_et.error = "Please Enter an address for your Shop"}
            else if (TextUtils.isEmpty(shopBvn)){bvn_et.error = "Please Enter your BVN to enable payment"}
        }else

        {
            uploadImage()

    }

    }


    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    fun getFileExtension( uri: Uri) : String {
        var cR = getContentResolver()
        var mime = MimeTypeMap.getSingleton();
        return mime!!.getExtensionFromMimeType(cR.getType(uri).toString())
    }

    private fun uploadImage() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading Image")
            progressDialog.show()

            //getting the storage reference
            val sRef = storageReference!!.child(
                "business_image" + getFileExtension(filePath!!)
            )

            //adding the file to reference
            sRef.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot ->
                    //dismissing the progress dialog
                    progressDialog.dismiss()

                    //displaying success toast
                    Toast.makeText(applicationContext, "File Uploaded ", Toast.LENGTH_LONG).show()

                    //creating the upload object to store uploaded image details
//                    sRef.downloadUrl.addOnCompleteListener{ uri ->
//                        if (uri.isSuccessful){
//                             downloadUrl = uri.result.toString()
//                        }else {
//                            toast(uri.exception!!.message.toString())
//                        }
//
//                        image_url = uri.toString()
//                        toast(image_url!!)
//                    }
                     upload = Upload(shop_name_et.text.toString().trim(), taskSnapshot.uploadSessionUri.toString())

                    image_url = taskSnapshot.uploadSessionUri.toString()

                        uploadBussinessDetails()
                    //adding an upload to firebase database
//                    val uploadId = mDatabase!!.push().key
//                    mDatabase!!.child(uploadId!!).setValue(upload)
                }
                .addOnFailureListener { exception ->
                    progressDialog.dismiss()
                    toast(exception.message!!)
                }
                .addOnProgressListener { taskSnapshot ->
                    //displaying the upload progress
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }
        } else {
            //display an error if no file is selected
            toast("Please Select an Image")
        }
    }

    fun uploadBussinessDetails(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Adding Business Information to Database")
        progressDialog.show()

         val shop= Shops(shopName!!, shopAddress!!, shopBvn!!, image_url!!)
         var database: DatabaseReference = FirebaseDatabase.getInstance().reference
// ...
        database!!.child("vendor_stores").child(userId!!).setValue(shop).
                addOnSuccessListener {

                    progressDialog.dismiss()
                    toast("Upload Complete")
                    startActivity(Intent(this, AddServiceActivity::class.java))
                    finish()
                }.
                addOnFailureListener{exception ->
                    toast(exception.message!!)

                }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                business_image!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
