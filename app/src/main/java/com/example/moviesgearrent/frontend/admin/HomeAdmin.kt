package com.example.moviesgearrent.frontend

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
//import coil.compose.AsyncImage
import com.example.moviesgearrent.BottomNavigationAdmin
import com.example.moviesgearrent.PreferencesManager
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
fun HomeAdmin(navController: NavController, context: Context = LocalContext.current) {
    val listProduk = remember { mutableStateListOf<ProdukRespon>() }
    var search by remember { mutableStateOf(TextFieldValue("")) }
    val baseColor = Color(0xFF00687A)
    val preferencesManager = remember { PreferencesManager(context) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HomeService::class.java)
    val call = retrofit.getDataAdmin("*",search.text, "createdAt:asc")
    call.enqueue(
        object : Callback<ApiRespon<List<ProdukRespon>>> {
            override fun onResponse(
                call: Call<ApiRespon<List<ProdukRespon>>>,
                response: Response<ApiRespon<List<ProdukRespon>>>
            ) {
                if (response.isSuccessful) {
                    Log.d("respond test", response.body().toString())
                    listProduk.clear()
                    response.body()?.data!!.forEach { Produks ->
                        listProduk.add(Produks)
                        Log.d("test get data", Produks.id.toString())
                    }
                } else {
                    try {
                        val jObjError =
                            JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            context,
                            jObjError.getJSONObject("error")
                                .getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("error try", jObjError.getJSONObject("error").getString("message"))
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("error respond", e.message.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ApiRespon<List<ProdukRespon>>>, t: Throwable) {
                Toast.makeText(
                    context,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("error respond", t.message.toString())
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MoviesGearRent", modifier = Modifier.padding(top = 5.dp), fontWeight = FontWeight.Bold, fontSize = 24.sp,)
                    IconButton(modifier = Modifier.padding(start = 320.dp), onClick = {
                        preferencesManager.saveData("jwt", "")
                        navController.navigate("login")
                    }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White,
                ),
            )
        },

        bottomBar = {
            BottomAppBar {
                BottomNavigationAdmin(navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("Addpage")
                },
                shape = RoundedCornerShape(50),
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),

                trailingIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = baseColor,

                            )

                    }
                },
                placeholder = { Text(text = "Cari", color = baseColor) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = baseColor,
                    unfocusedBorderColor =baseColor,
                    cursorColor = baseColor,
                    textColor = baseColor,
                )
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(listProduk.size) { index ->
                    val id = listProduk[index].id
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(2.dp)
                            .padding(10.dp)
                            .clickable(onClick = { navController.navigate("DetailAdmin/$id") })
                    ) {
                        val currentValue = listProduk[index].attribute?.foto_produk?.data?.attributes?.url
                        val newUrl = currentValue?.replace("/uploads/", "::uploads::")
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .clickable { navController.navigate("detailadmin/" + listProduk[index].id + "/" + listProduk[index].attribute?.nama_produk + "/" + listProduk[index].attribute?.desc_produk + "/" + listProduk[index].attribute?.harga + "/" + listProduk[index].attribute?.status + "/" + newUrl)  },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
//                            println(listProduk[index].attribute?.foto_produk?.data!!.attributes.url+"ahahahhahhaha")
                            Image(
                                painter = rememberAsyncImagePainter("http://10.0.2.2:1337" + listProduk[index].attribute?.foto_produk?.data!!.attributes.url),
                                contentDescription = "Produk",
                                modifier = Modifier
                                    .size(150.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = listProduk[index].attribute?.nama_produk.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(600),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                text = "Rp. " + listProduk[index].attribute?.harga.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                        }

                    }

                }
            }


//            LazyVerticalGrid(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                columns = GridCells.Fixed(2),
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//            ) {
//                items(listProduk.size) { index ->
//                    val id = listProduk[index].id
////                    Box(
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .clip(RoundedCornerShape(15.dp))
////                            .shadow(2.dp)
////                            .padding(10.dp)
////                            .clickable(onClick = { navController.navigate("DetailPage/$id") })
////                    ) {
////
////                        Column(
////                            modifier = Modifier
////                                .fillMaxSize()
////                                .padding(10.dp)
////                                .clickable { navController.navigate("detailadmin/" + listProduk[index].id + "/" + listProduk[index].attribute?.nama_produk + "/" + listProduk[index].attribute?.desc_produk + "/" + listProduk[index].attribute?.harga + "/" + listProduk[index].attribute?.status) },
////                            horizontalAlignment = Alignment.CenterHorizontally,
////                            verticalArrangement = Arrangement.Center
////                        ) {
//                            println(listProduk[index].attribute?.foto_produk?.data!!.attributes.url+"ahahahhahhaha")
////                            Image(
////                                painter = rememberAsyncImagePainter("http://10.0.2.2:1337" + listProduk[index].attribute?.foto_produk?.data!!.attributes.url),
////                                contentDescription = "Produk",
////                                modifier = Modifier
////                                    .size(150.dp)
////                                    .align(Alignment.CenterHorizontally)
////                            )
//                            println(listProduk[index].attribute?.nama_produk+"dkasdksadaghdgda")
//                            println(listProduk[index].attribute?.harga.toString()+"dkasdksadaghdgda")
////                            Text(
////                                text = "listProduk[index].attribute?.nama_produk",
////                                style = TextStyle(
////                                    fontSize = 20.sp,
////                                    fontWeight = FontWeight(600),
////                                    color = MaterialTheme.colorScheme.primary
////                                )
////                            )
////                            Text(
////                                text = "Rp. " + listProduk[index].attribute?.harga.toString(),
////                                style = TextStyle(
////                                    fontSize = 20.sp,
////                                    fontWeight = FontWeight(600),
////                                    color = Color.Black
////                                )
////                            )
////                        }
//
//                    }
//
//                }

            }
        }

    }






