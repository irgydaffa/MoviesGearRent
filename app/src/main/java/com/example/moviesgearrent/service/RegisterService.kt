package com.example.moviesgearrent.service

import com.example.moviesgearrent.respon.LoginRespon
import com.example.moviesgearrent.data.RegisterData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("auth/local/register")
    fun saveData(@Body body: RegisterData) : Call<LoginRespon>
}