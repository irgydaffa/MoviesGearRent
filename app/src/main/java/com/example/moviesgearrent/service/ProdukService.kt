package com.example.moviesgearrent.service

import com.example.moviesgearrent.data.ProdukDataWrapper
import com.example.moviesgearrent.respon.ApiResponse
import com.example.moviesgearrent.respon.ProdukRespon
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


data class DataWrapper(@SerializedName("data") val dataP: dataProduk)

data class dataProduk(
    val desc_produk: String,
    val nama_produk: String,
    val harga: Int
)
interface ProdukService {
    @POST("produks")
    fun addProduk(@Body dataProduk: DataWrapper): Call<ApiResponse<ProdukRespon>>

}

class UploadResponse {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    var name: String = ""
    @SerializedName("url")
    var url: String = ""
}

class UploadResponseList : ArrayList<UploadResponse>()

data class UploadRequestBody(
    @Part("ref") val ref: RequestBody,
    @Part("refId") val refId: RequestBody,
    @Part("field") val field: RequestBody,
    @Part("files") var file: MultipartBody.Part
)
internal interface ImgService {
    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part("ref") ref: RequestBody,
        @Part("refId") refId: RequestBody,
        @Part("field") field: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<UploadResponseList>
}