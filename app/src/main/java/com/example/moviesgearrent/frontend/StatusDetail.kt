package com.example.moviesgearrent.frontend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.moviesgearrent.R
import com.example.moviesgearrent.data.StatusData
import com.example.moviesgearrent.data.StatusDataWrapper
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.service.HomeService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDetail(
    navController: NavController,
    id: String?,
    nama: String?,
    status: String?,
    context: Context = LocalContext.current
) {
    val baseUrl = "http://10.0.2.2:1337/api/"
    var search by remember { mutableStateOf(TextFieldValue("")) }
    val listProduk = remember { mutableStateOf(ProdukRespon()) }
    val id_produk = remember { mutableStateOf(id ?: "0") }
    val nama_produk = remember { mutableStateOf(nama ?: "") }
    val status = remember { mutableStateOf(status ?: "") }


//    val retrofit =
//        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
//            .build().create(HomeService::class.java)
//    val call = retrofit.getDetailData(id!!)
//    call.enqueue(object : Callback<ApiRespon<ProdukRespon>> {
//        override fun onResponse(
//            call: Call<ApiRespon<ProdukRespon>>,
//            response: Response<ApiRespon<ProdukRespon>>
//        ) {
//            if (response.code() == 200) {
//                val resp = response.body()?.data
//                nama_produk.value = resp?.attribute?.nama_produk!!
//                desc_produk.value = resp?.attribute?.desc_produk!!
//                status.value = resp?.attribute?.status!!
//                if (resp?.attribute?.status == "tersedia") {
//                    status.value = "Tersedia"
//                } else if (resp?.attribute?.status == "disewa") {
//                    status.value = "Sedang Disewa"
//                } else if (resp?.attribute?.status == "selesai") {
//                    status.value = "Selesai"
//                }
//            } else if (response.code() == 400) {
//                print("error login")
//                Toast.makeText(
//                    context, "Username atau password salah", Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//        override fun onFailure(call: Call<ApiRespon<ProdukRespon>>, t: Throwable) {
//            print(t.message)
//        }
//    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.navigate("homepage") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Detail Status",
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.sp,
                        )
                    }
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                )
            )
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
            }
            Box {
                Column(
                    modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .width(320.dp)
                        .padding(20.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 12.dp)

                        )
                )


                {
                    Text(
                        text = nama_produk.value,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp, top = 10.dp)
                    )
//                    Text(
//                        text = harga.value.toString(),
//                        fontSize = 14.sp,
//                        modifier = Modifier
//                            .padding(bottom = 10.dp, top = 15.dp)
//                    )
                    Text(
                        text = status.value,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp, top = 15.dp)
                    )

                }
            }
            val retrofit2 = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HomeService::class.java)
            val data = StatusData("dipinjam")
            if (status.value.equals("pending")) {
                Button(onClick = {
                    val call2 = retrofit2.UpdateStatus(id_produk.value, StatusDataWrapper(data))
                    call2.enqueue(
                        object : Callback<ApiRespon<ProdukRespon>> {

                            override fun onResponse(
                                call: Call<ApiRespon<ProdukRespon>>,
                                response: Response<ApiRespon<ProdukRespon>>
                            ) {
                                if (response.isSuccessful) {
                                    navController.navigate("statuspage")
                                } else if (response.code() == 400) {
                                    try {
                                        val jObjError = JSONObject(response.errorBody()!!.string())
                                        Toast.makeText(
                                            context,
                                            jObjError.getJSONObject("error").getString("message"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<ApiRespon<ProdukRespon>>,
                                t: Throwable
                            ) {
                                print(t.message)
                            }
                        }
                    )
                }
                ) {
                    Text("Ubah Status")
                }
            }
            val retrofit3 = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HomeService::class.java)
            val data2 = StatusData("selesai")
            if (status.value.equals("disewa")) {
                Button(onClick = {
                    val call2 = retrofit3.UpdateStatus(id_produk.value, StatusDataWrapper(data2))
                    call2.enqueue(
                        object : Callback<ApiRespon<ProdukRespon>> {

                            override fun onResponse(
                                call: Call<ApiRespon<ProdukRespon>>,
                                response: Response<ApiRespon<ProdukRespon>>
                            ) {
                                if (response.isSuccessful) {
                                    navController.navigate("statuspage")
                                } else if (response.code() == 400) {
                                    try {
                                        val jObjError = JSONObject(response.errorBody()!!.string())
                                        Toast.makeText(
                                            context,
                                            jObjError.getJSONObject("error").getString("message"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<ApiRespon<ProdukRespon>>,
                                t: Throwable
                            ) {
                                print(t.message)
                            }
                        }
                    )
                }
                ) {
                    Text("Ubah Status")
                }
            }
            val retrofit4 = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HomeService::class.java)
            val data3 = StatusData("tersedia")
            if (status.value.equals("selesai")) {
                Button(onClick = {
                    val call2 = retrofit4.UpdateStatus(id_produk.value, StatusDataWrapper(data3))
                    call2.enqueue(
                        object : Callback<ApiRespon<ProdukRespon>> {

                            override fun onResponse(
                                call: Call<ApiRespon<ProdukRespon>>,
                                response: Response<ApiRespon<ProdukRespon>>
                            ) {
                                if (response.isSuccessful) {
                                    navController.navigate("statuspage")
                                } else if (response.code() == 400) {
                                    try {
                                        val jObjError = JSONObject(response.errorBody()!!.string())
                                        Toast.makeText(
                                            context,
                                            jObjError.getJSONObject("error").getString("message"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<ApiRespon<ProdukRespon>>,
                                t: Throwable
                            ) {
                                print(t.message)
                            }
                        }
                    )
                }) {
                    Text("Ubah Status")
                }
            }
        }
    }
}