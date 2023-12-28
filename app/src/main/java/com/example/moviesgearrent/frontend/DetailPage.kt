package com.example.moviesgearrent.frontend

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
fun Detailpage(
    navController: NavController,
    id: String?,
    context: Context = LocalContext.current
) {
    val listProduk = remember { mutableStateOf(ProdukRespon()) }
    val nama_produk = remember { mutableStateOf("") }
    val desc_produk = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("") }
    val harga = remember { mutableStateOf(0) }
    val baseUrl = "http://10.0.2.2:1337/api/"
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
//                harga.value = resp?.attribute?.harga!!
//                Log.d("test data", listProduk.attribute?.nama_produk.toString())
//                val resp = response.body()?.data
//                resp?.let { listProduk ->
//                    if (listProduk.is) {
//                        val id = dataList[0].id
//                        boothID.intValue = id
//
//                        val boothNames = dataList[0].attributes?.boothName
//                        boothName.value = boothNames
//
//                        val boothDescription = dataList[0].attributes?.boothDescription
//                        boothDesc.value = boothDescription
//
//                        val openBooth = dataList[0].attributes?.open
//                        open.value = openBooth
//                    }
//
//                }
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
//    Text(text = listProduk.value.attribute?.nama_produk!!)
//    Log.d("test data", listProduk.attribute?.nama_produk.toString())
    Column {
        Text(text = nama_produk.value)
        Text(text = desc_produk.value)
        Text(text = status.value)
    }

//    Text(text = harga.value)
}