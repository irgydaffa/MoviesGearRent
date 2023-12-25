package com.example.moviesgearrent.respon

import com.google.gson.annotations.SerializedName

class LoginRespon {
    @SerializedName("jwt")
    var jwt : String = ""
}

class ApiRespon<T>{
    @SerializedName("data")
    var data : T? = null
}