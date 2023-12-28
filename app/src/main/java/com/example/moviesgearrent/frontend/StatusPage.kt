package com.example.moviesgearrent.frontend

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.service.HomeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun StatusPage(navController: NavController, id: String?, context: Context = LocalContext.current) {
    val baseUrl = "http://10.0.2.2:1337/api/"
    val listProduk = remember { mutableStateOf(ProdukRespon()) }
    val nama_produk = remember { mutableStateOf("") }
    val desc_produk = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("") }
    val harga = remember { mutableStateOf(0) }
    val retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build().create(HomeService::class.java)
    val call = retrofit.getDetailData(id!!)
    call.enqueue(object : Callback<ApiRespon<ProdukRespon>> {
        override fun onResponse(
            call: Call<ApiRespon<ProdukRespon>>,
            response: Response<ApiRespon<ProdukRespon>>
        ) {
            if (response.code() == 200) {
                val resp = response.body()?.data
                nama_produk.value = resp?.attribute?.nama_produk!!
                desc_produk.value = resp?.attribute?.desc_produk!!
                status.value = resp?.attribute?.status!!
                harga.value = resp?.attribute?.harga!!
                if (resp?.attribute?.status == "tersedia") {
                    status.value = "Tersedia"
                } else if (resp?.attribute?.status == "disewa"){
                    status.value = "Sedang Disewa"
                } else if(resp?.attribute?.status == "selesai"){
                    status.value = "Selesai"
                }
            } else if (response.code() == 400) {
                print("error login")
                Toast.makeText(
                    context, "Username atau password salah", Toast.LENGTH_SHORT
                ).show()
            }
        }
        override fun onFailure(call: Call<ApiRespon<ProdukRespon>>, t: Throwable) {
            print(t.message)
        }
    })
}