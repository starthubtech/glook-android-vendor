package com.example.android.glook

import android.app.ProgressDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ProgressBar


//fun showProgressDialog(message : String, context: Context){
//   var rootLayout: ConstraintLayout? = null
//    var progressBar : ProgressBar? = null
//    progressBar =  ProgressBar(context, null, android.R.attr.progressBarStyleLargeInverse)
//    var params: ConstraintLayout.LayoutParams  = ConstraintLayout.LayoutParams(100,100)
//    rootLayout!!.addView(progressBar, params)
//   // progressBar!!.visibility(View.VISIBLE)
//
//
//}

interface OnItemClickListener {
    fun onItemClick(item: HomePoko)
}

//fun showMessage(view: View, message: String){
//    Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
//}