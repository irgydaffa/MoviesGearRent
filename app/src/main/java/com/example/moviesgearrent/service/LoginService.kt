package com.example.moviesgearrent.service


import com.example.moviesgearrent.respon.LoginRespon
import com.example.moviesgearrent.data.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/local")
    fun getData(@Body body: LoginData) : Call<LoginRespon>
}