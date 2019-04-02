package com.example.android.glook

import com.google.firebase.database.IgnoreExtraProperties

data class User(val firstName: String, val lastName: String, val email: String, val password: String, val phone: String)



@IgnoreExtraProperties
data class Shops(val shopName: String, val shopAdress: String, val shopBvn: String, val upload: Upload)

@IgnoreExtraProperties
data class Upload(var name: String?, var url: String)

@IgnoreExtraProperties
data class Service(var serviceName: String, var serviceCost: String)

