package com.example.moviesgearrent.service

import com.example.moviesgearrent.data.StatusData
import com.example.moviesgearrent.data.StatusDataWrapper
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("produks")
    fun getData(
     @Query("populate") populate: String = "*",
     @Query("sort") sort: String = "createdAt:asc"
    ): Call<ApiRespon<List<ProdukRespon>>>

    @GET("produks/{id}")
    fun getDetailData(
        @Path("id") id: String,
        @Query("populate") populate: String = "*",
        @Query("sort") sort: String = "createdAt:asc",
//        @Query("filters")
    ): Call<ApiRespon<ProdukRespon>>

    @PUT("produks/{id}")
    fun UpdateStatus(
        @Path("id") id: String,
        @Body status: StatusDataWrapper
    ): Call<ApiRespon<ProdukRespon>>
}