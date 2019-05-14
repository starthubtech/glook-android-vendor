package com.example.android.glook

import com.google.firebase.database.IgnoreExtraProperties

data class User(val firstName: String, val lastName: String, val email: String, val password: String, val phone: String)



@IgnoreExtraProperties
data class Shops(val shopName: String, val shopAdress: String, val shopBvn: String, val image_url: String)

@IgnoreExtraProperties
data class Upload(var name: String?, var url: String)

@IgnoreExtraProperties
data class Service(var serviceName: String, var serviceCost: String, var userId: String)

data class HomePoko(var actionImage: Int, var actionDesc: String)

data class CardPoko(var accountName: String, var accountNumber: String, var bankName: String)

class Model {

    var name: String? = null
    var image_drawable: Int = 0

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getImage_drawables(): Int {
        return image_drawable
    }

    fun setImage_drawables(image_drawable: Int) {
        this.image_drawable = image_drawable
    }

}

