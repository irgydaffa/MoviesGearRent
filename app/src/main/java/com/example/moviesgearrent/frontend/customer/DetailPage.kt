package com.example.moviesgearrent.frontend.customer

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.moviesgearrent.data.StatusDataWrapper
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviesgearrent.data.StatusData
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.service.HomeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.moviesgearrent.R
import org.json.JSONObject
import java.lang.Exception

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detailpage(
    navController: NavController,
    id: String?,
    nama: String?,
    desc: String?,
    harga: String?,
    status: String?,
    newUrl: String?,
    context: Context = LocalContext.current
) {
    val listProduk = remember { mutableStateOf(ProdukRespon()) }
    val id_produk = remember { mutableStateOf(id ?: "0") }
    val nama_produk = remember { mutableStateOf(nama ?: "") }
    val desc_produk = remember { mutableStateOf(desc ?: "") }
    val status = remember { mutableStateOf(status ?: "") }
    val harga = remember { mutableStateOf(harga ?: "") }

    val baseUrl = "http://10.0.2.2:1337/api/"

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
                            text = "Detail Produk",
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
                val currentValue = newUrl ?: ""
                val editUrl = currentValue.replace("::uploads::", "/uploads/")
                Image(
                    painter = rememberAsyncImagePainter("http://10.0.2.2:1337" + editUrl),
                    contentDescription = "Produk",
                    modifier = Modifier
                        .size(320.dp, 320.dp)
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

                    Text(
                        text = "Rp. "+harga.value,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(bottom = 5.dp, top = 10.dp)
                    )

                    Text(
                        text = desc_produk.value,
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
            val data = StatusData("pending")
            if (status.value.equals("tersedia")) {
                Button(onClick = {
                    val call2 = retrofit2.UpdateStatus(id_produk.value, StatusDataWrapper(data))
                    call2.enqueue(
                        object : Callback<ApiRespon<ProdukRespon>> {

                            override fun onResponse(
                                call: Call<ApiRespon<ProdukRespon>>,
                                response: Response<ApiRespon<ProdukRespon>>
                            ) {
                                if (response.isSuccessful) {
                                    navController.navigate("HomePage")
                                } else if (response.code() == 400) {
                                    try {
                                        val JObjError = JSONObject(response.errorBody()!!.string())
                                        Toast.makeText(
                                            context,
                                            JObjError.getJSONObject("error").getString("message"),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }catch (e: Exception){
                                        Toast.makeText(
                                            context,
                                            e.message,
                                            Toast.LENGTH_LONG
                                        )
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
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text("Sewa")
                }
            } else if (status.value.equals("disewa")) {
                Text(text = "Sedang disewa")
            } else if (status.value.equals("selesai")) {
                Text(text = "Belum tersedia")
            }
        }
    }
}