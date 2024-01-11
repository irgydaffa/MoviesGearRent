package com.example.moviesgearrent.frontend

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.moviesgearrent.BottomNavigationAdmin
import com.example.moviesgearrent.respon.ApiResponse
import com.example.moviesgearrent.respon.ProdukRespon
import com.example.moviesgearrent.service.DataWrapper
import com.example.moviesgearrent.service.ImgService
import com.example.moviesgearrent.service.ProdukService
import com.example.moviesgearrent.service.UploadResponseList
import com.example.moviesgearrent.service.dataProduk
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Addpage(navController: NavController, context: Context = LocalContext.current) {
    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val desc = remember { mutableStateOf(TextFieldValue("")) }
    val harga = remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageFile by remember { mutableStateOf<File?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    val resolver = context.contentResolver
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                resolver.openInputStream(selectedImageUri!!)?.let { inputStream ->
                    val originalFileName = context.contentResolver.query(
                        selectedImageUri!!, null, null, null, null
                    )?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val displayNameIndex =
                                cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (displayNameIndex != -1) {
                                cursor.getString(displayNameIndex)
                            } else {
                                null
                            }
                        } else {
                            null
                        }
                    }
                    val file = File(context.cacheDir, originalFileName ?: "temp_img.jpg")
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    selectedImageFile = file
                }
            }
        })
    Scaffold(

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
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add Produk",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            OutlinedTextField(
                value = nama.value,
                onValueChange = { nama.value = it },
                label = { Text("Nama Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            OutlinedTextField(
                value = harga.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {harga.value = it},
                label = { Text("Harga Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            TextField(
                value = desc.value,
                onValueChange = { desc.value = it },
                label = { Text("Deskripsi Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            Row(
                modifier = androidx.compose.ui.Modifier
                    .padding(top = 8.dp)
            ) {
                Box(modifier = androidx.compose.ui.Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clickable { pickImageLauncher.launch("image/*") }
                    .border(
                        width = 1.5.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(8.dp)
                    ), contentAlignment = Alignment.Center) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberImagePainter(data = selectedImageUri, builder = {
                                transformations(CircleCropTransformation())
                            }),
                            contentDescription = "Selected Image",
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(8.dp))
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Photo",
                            tint = Color.Blue
                        )
                    }
                }

                Spacer(modifier = androidx.compose.ui.Modifier.width(8.dp))

                if (selectedImageUri != null) {
                    IconButton(
                        onClick = { selectedImageUri = null }, modifier = androidx.compose.ui.Modifier.size(
                            48.dp
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Image")
                    }
                }
            }
            Button(
                onClick = {
                    val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(ProdukService::class.java)
                    try {
                        val price = harga.value.text.toInt()
                        val dataP = DataWrapper(
                            dataProduk(
                                desc.value.text,
                                nama.value.text,
                                price
                            )
                        )
                        val call = retrofit.addProduk(dataP)

                        call.enqueue(object : Callback<ApiResponse<ProdukRespon>> {
                            override fun onResponse(
                                call: Call<ApiResponse<ProdukRespon>>,
                                response: Response<ApiResponse<ProdukRespon>>
                            ) {
                                if (response.isSuccessful) {
                                    val r = response.body()!!.data
                                    val id = response.body()!!.data!!.id
                                    val file = selectedImageFile
                                    val mimeType =
                                        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                            file!!.extension
                                        )
                                    val refRequestBody =
                                        "api::produk.produk".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                                    val refIdRequestBody = id.toString()
                                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                                    val fieldRequestBody =
                                        "foto_produk".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                                    val fileRequestBody = MultipartBody.Part.createFormData(
                                        "files",
                                        file.name,
                                        file.asRequestBody(mimeType?.toMediaTypeOrNull())
                                    )

                                    val retrofit2 = Retrofit.Builder().baseUrl(baseUrl)
                                        .addConverterFactory(GsonConverterFactory.create()).client(
                                            OkHttpClient.Builder().addInterceptor(
                                                HttpLoggingInterceptor().setLevel(
                                                    HttpLoggingInterceptor.Level.BODY
                                                )
                                            ).build()
                                        )
                                        .build().create(ImgService::class.java)
                                    val call2 = retrofit2.uploadImage(
                                        refRequestBody,
                                        refIdRequestBody,
                                        fieldRequestBody,
                                        fileRequestBody
                                    )
                                    call2.enqueue(object : Callback<UploadResponseList> {
                                        override fun onResponse(
                                            call12: Call<UploadResponseList>,
                                            response12: Response<UploadResponseList>
                                        ) {
                                            if (response12.isSuccessful) {
                                           navController.navigate("homepage")
                                               Toast.makeText(
                                                    context,
                                                    "Berhasil menambahkan produk",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Error: ${response.code()} - ${response.message()}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        override fun onFailure(
                                            call12: Call<UploadResponseList>, t: Throwable
                                        ) {
                                            Toast.makeText(
                                                context,
                                                "Error: ${response.code()} - ${response.message()}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                                } else {
                                    Toast.makeText(
                                        context, "Error: ${response.code()}", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(
                                call: Call<ApiResponse<ProdukRespon>>,
                                t: Throwable
                            ) {
                                print(t.message)
                            }

                        })
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            context,
                            "Error: Invalid price format",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            ) {
                Text("Add")
            }
        }
    }
}

