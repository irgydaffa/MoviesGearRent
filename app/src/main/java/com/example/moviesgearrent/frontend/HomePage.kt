package com.example.moviesgearrent.frontend

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import coil.compose.AsyncImage
import com.example.moviesgearrent.BottomNavigation
import com.example.moviesgearrent.PreferencesManager
import com.example.moviesgearrent.R
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
fun Homepage(navController: NavController, context: Context = LocalContext.current) {
    val listProduk = remember { mutableStateListOf<ProdukRespon>() }
    var searchfield by remember { mutableStateOf(TextFieldValue("")) }

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
            var searchLabel by remember {
                mutableStateOf("Search")
            }
            TopAppBar(
                modifier = Modifier
                    .height(170.dp)
                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = { Text(text = "Homepage") },
            )


            Text(text = "SahityaDisini",
                modifier= Modifier
                    .padding(30.dp)
                    .size(150.dp),
                    color = Color.White

            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    modifier= Modifier.size(45.dp),
                    tint = Color.White

                )


            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 75.dp)
                ,

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(
                    value = searchfield,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier.width(500.dp),
                    onValueChange = { newText ->
                        searchfield = newText
                    },
                    label = { Text(searchLabel, color = Color.White)},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Search,"Search", tint = Color.White)
                    },
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigation(navController)
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
                    val id = listProduk[index].id
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(2.dp)
                            .padding(10.dp)
                            .clickable(onClick = { navController.navigate("DetailPage/$id") })
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .clickable { navController.navigate("detailpage/" + listProduk[index].id + "/" + listProduk[index].attribute?.nama_produk + "/" + listProduk[index].attribute?.desc_produk + "/" + listProduk[index].attribute?.harga + "/" + listProduk[index].attribute?.status) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
//                            DisplayImageFromUrl(imageUrl = listProduk[index].attribute?.)
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
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

//@Composable
//fun DisplayImageFromUrl(imageUrl: String) {
//    val Url = "http://10.0.2.2:1337/api/"+imageUrl
//    AsyncImage(
//        model = Url,
//        placeholder = painterResource(id = R.drawable.tokoimg),
//        error = painterResource(id = R.drawable.ic_launcher_background),
//        contentDescription = "Test"
//    )
//}
