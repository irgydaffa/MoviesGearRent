package com.example.moviesgearrent.service

import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import retrofit2.Call
import retrofit2.http.GET
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
}