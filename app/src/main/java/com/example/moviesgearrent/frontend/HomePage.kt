package com.example.moviesgearrent.frontend

import android.content.ClipData.Item
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesgearrent.PreferencesManager
import com.example.moviesgearrent.R
import com.example.moviesgearrent.respon.ApiRespon
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.respon.UserRespon
import com.example.moviesgearrent.service.HomeService
import com.example.moviesgearrent.service.UserService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(navController: NavController, context: Context = LocalContext.current) {
    val listProduk = remember { mutableStateListOf<ProdukRespon>() }
    val preferencesManager = remember { PreferencesManager(context) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HomeService::class.java)
    val call = retrofit.getData()
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
                        Log.d("test", Produks.id.toString())
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
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = { Text(text = "Homepage") },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { navController.navigate("DetailPage") }),
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("homepage")
                        }
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Add",

                                modifier = Modifier.size(30.dp)

                            )
                            Text(
                                text = "Beranda",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("login") }
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",

                                modifier = Modifier.size(30.dp)

                            )
                            Text(
                                text = "Notifikasi",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("createuser") }

                    )
                    {
                        Column {
                            Icon(
                                Icons.Outlined.History,
                                contentDescription = "Account",

                                modifier = Modifier.size(30.dp)

                            )
                            Text(
                                text = "History",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                        }
                    }
                    IconButton(
                        onClick = { navController.navigate("createuser") }

                    )
                    {
                        Column {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Account",

                                modifier = Modifier.size(30.dp)

                            )
                            Text(
                                text = "Profil",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                        }
                    }

                }
            }
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(listProduk.size) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .shadow(7.dp)
                            .padding(10.dp)
                            .clickable(onClick = {navController.navigate ("DetailPage/{id}")})
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Produk",
                                modifier = Modifier
                                    .size(110.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = listProduk[index].attribute?.nama_produk.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.Black
                                )
                            )
                            Text(
                                text = listProduk[index].attribute?.harga.toString(),
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
            listProduk.forEach { Produks ->
                Log.d("Produk", Produks.attribute?.nama_produk.toString())
            }
        }

    }
}


