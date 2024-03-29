package com.example.moviesgearrent.frontend

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.md_theme_dark_onPrimary
import com.example.compose.md_theme_dark_primary
import com.example.moviesgearrent.BottomNavigationAdmin
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusPage(navController: NavController, id: String?, context: Context = LocalContext.current) {
    val baseUrl = "http://10.0.2.2:1337/api/"
    val listProduk = remember { mutableStateListOf<ProdukRespon>()}
    val nama_produk = remember { mutableStateOf("") }
    val desc_produk = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("") }
    val harga = remember { mutableStateOf(0) }
     val retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build().create(HomeService::class.java)
    val call = retrofit.getDataAdmin(search = null, status = "pending")
    call.enqueue(object : Callback<ApiRespon<List<ProdukRespon>>> {
        override fun onResponse(
            call: Call<ApiRespon<List<ProdukRespon>>>,
            response: Response<ApiRespon<List<ProdukRespon>>>
        ) {
            if (response.isSuccessful) {
                Log.d("respond test", response.body().toString())
                listProduk.clear()
                response.body()?.data!!.forEach { Produks ->
                    listProduk.add(Produks)
                    Log.d("test", Produks.id.toString())
                }}
            else  {
                print("error login")
                Toast.makeText(
                    context, "Username atau password salah", Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<ApiRespon<List<ProdukRespon>>>, t: Throwable) {
            print(t.message)
        }
    })

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
                            text = "Status Page", fontWeight = FontWeight.Medium, fontSize = 25.sp,
                        )
                    }
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigationAdmin(navController)
            }
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .drawBehind {
                            val borderSize = 4.dp.toPx()
                            drawLine(
                                color = md_theme_dark_onPrimary,
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height),
                                strokeWidth = borderSize
                            )
                        }
                        .clickable(onClick = {
                            navController.navigate("statuspage")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Pending")
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .drawBehind {
                            val borderSize = 4.dp.toPx()
                            drawLine(
                                color = md_theme_dark_primary,
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height),
                                strokeWidth = borderSize
                            )
                        }
                        .clickable(onClick = {
                            navController.navigate("dipinjam")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Disewa")
                }
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .drawBehind {
//                            val borderSize = 4.dp.toPx()
//                            drawLine(
//                                color = md_theme_dark_onPrimary,
//                                start = Offset(x = 0f, y = size.height),
//                                end = Offset(x = size.width, y = size.height),
//                                strokeWidth = borderSize
//                            )
//                        }
//                        .clickable(onClick = {
//                            navController.navigate("selesai")
//                        }),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Text(text = "Selesai")
//                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .drawBehind {
                            val borderSize = 4.dp.toPx()
                            drawLine(
                                color = md_theme_dark_primary,
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height),
                                strokeWidth = borderSize
                            )
                        }
                        .clickable(onClick = {
                            navController.navigate("tersedia")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Tersedia")
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
            listProduk.forEach {
                Box {
                    Row(
                        modifier = Modifier
                            .shadow(
                                elevation = 5.5.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000)
                            )
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 3.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        {
                            Text(
                                text = it.attribute?.nama_produk.toString(),
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(bottom = 10.dp, top = 10.dp)
                            )

                            Text(
                                text = it.attribute?.harga.toString(),
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(bottom = 5.dp, top = 10.dp)
                            )

                            Text(
                                text = it.attribute?.status.toString(),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(bottom = 10.dp, top = 15.dp)
                            )
                        }
                        Button(onClick = {
                            val retrofit2 = Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                .create(HomeService::class.java)
                            val call2 = retrofit2.UpdateStatus(it.id.toString(), StatusDataWrapper(StatusData("disewa")))
                            call2.enqueue(
                                object : Callback<ApiRespon<ProdukRespon>> {
                                    override fun onResponse(
                                        call: Call<ApiRespon<ProdukRespon>>,
                                        response: Response<ApiRespon<ProdukRespon>>
                                    ) {

                                        if (response.code() == 200) {
                                            navController.navigate("statuspage")
                                        } else if (response.code() == 400) {
                                            try {
                                                val jObjError =
                                                    JSONObject(response.errorBody()!!.string())
                                                Toast.makeText(
                                                    context,
                                                    jObjError.getJSONObject("error")
                                                        .getString("message"),
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
                                    override fun onFailure(call: Call<ApiRespon<ProdukRespon>>, t: Throwable) {
                                        print(t.message)
                                    }
                                }
                            )
                        }
                        ) {
                            Text("Ubah Status")
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}

