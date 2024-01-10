package com.example.moviesgearrent.frontend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesgearrent.data.RegisterData
import com.example.moviesgearrent.respon.LoginRespon
import com.example.moviesgearrent.service.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(navController: NavController, context: Context = LocalContext.current) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
        IconButton(
            modifier = Modifier
                .padding(top = 25.dp, start = 21.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                ),
            onClick = { navController.navigate("login") }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Account",
                modifier = Modifier.size(25.dp),
                tint = Color.White
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            shape = RoundedCornerShape(30.dp),
            onValueChange = { newText ->
                username = newText
            },
            label = { Text("Usename") })
        OutlinedTextField(
            value = email,
            shape = RoundedCornerShape(30.dp),
            onValueChange = { newText ->
                email = newText
            },
            label = { Text("Email") })
        OutlinedTextField(
            value = password,
            shape = RoundedCornerShape(30.dp),
            onValueChange = { newText ->
                password = newText
            },
            label = { Text("Password") })

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

        ElevatedButton(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White),
            onClick = {
                val baseUrl = "http://10.0.2.2:1337/api/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RegisterService::class.java)
                val call = retrofit.saveData(
                    RegisterData(
                        email,
                        username,
                        password
                    )
                )
                call.enqueue(object : Callback<LoginRespon> {
                    override fun onResponse(
                        call: Call<LoginRespon>,
                        response: Response<LoginRespon>
                    ) {
                        print(response.code())
                        if (response.code() == 200) {
                            Toast.makeText(
                                context,
                                "Register Sukses",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("login")
                        } else if (response.code() == 400) {
                            print(response.errorBody())
                            Toast.makeText(
                                context,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginRespon>, t: Throwable) {
                        print(t.message)
                    }
                })
            }
        ) {
            Text("Register")
        }
    }
}
