package com.example.moviesgearrent.frontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
<<<<<<< HEAD
import androidx.compose.material3.Button
=======
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
>>>>>>> 6ae2080551be5fd4d9b3307ffc211b11f6089f83
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
<<<<<<< HEAD
import com.example.moviesgearrent.data.StatusData
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.service.HomeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
=======
import com.example.moviesgearrent.R
>>>>>>> 6ae2080551be5fd4d9b3307ffc211b11f6089f83

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavController,  produkId : String?, namaParameter: String?, descParameter: String?, hargaParameter: String?) {
    var nama by remember { mutableStateOf(namaParameter?: "") }
    var desc by remember { mutableStateOf(descParameter?: "") }
    var harga by remember { mutableStateOf(hargaParameter?: "") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("homepage") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Detail Produk", fontWeight = FontWeight.Medium, fontSize = 25.sp,
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
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
<<<<<<< HEAD
            if (response.code() == 200) {
                val resp = response.body()?.data
                nama_produk.value = resp?.attribute?.nama_produk!!
                desc_produk.value = resp?.attribute?.desc_produk!!
                status.value = resp?.attribute?.status!!
                harga.value = resp?.attribute?.harga!!
                if (resp?.attribute?.status == "pending") {
                    status.value = "Pending"
                }
            } else if (response.code() == 400) {
                print("error login") 
                Toast.makeText(
                    context, "salah ya bang", Toast.LENGTH_SHORT
                ).show()
=======
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
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .width(320.dp)
                        .padding(20.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 12.dp)

                        )


                ) {
                    Text(text = nama,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp,top = 10.dp))

                    Text(text = harga,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(bottom = 5.dp, top = 10.dp))

                    Text(text = desc,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp,top = 15.dp))

                    ElevatedButton(onClick = {navController.navigate("pagesewa")},
                    ) {
                        Text(text = "Sewa")
                    }
                }
>>>>>>> 6ae2080551be5fd4d9b3307ffc211b11f6089f83
            }
        }

        }
<<<<<<< HEAD
    })
    Column {
        Text(text = nama_produk.value)
        Text(text = desc_produk.value)
        Text(text = status.value)
        Text(text = harga.value.toString())
=======
>>>>>>> 6ae2080551be5fd4d9b3307ffc211b11f6089f83
    }
    Button(onClick = {
        val retrofit2 = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeService::class.java)
        val call2 = retrofit.UpdateStatus(id!!, StatusData(status.value))
        call2.enqueue(
            object : Callback<ApiRespon<ProdukRespon>> {
                override fun onResponse(
                    call: Call<ApiRespon<ProdukRespon>>,
                    response: Response<ApiRespon<ProdukRespon>>
                ) {

                    if (response.code() == 200) {
                        navController.navigate("HomePage")
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
            }
        )

    }){
        Text ("Sewa")
    }

