package com.example.moviesgearrent.frontend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesgearrent.respon.LoginRespon
import com.example.moviesgearrent.PreferencesManager
import com.example.moviesgearrent.data.RegisterData
import com.example.moviesgearrent.service.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(navController: NavController, context: Context = LocalContext.current) {
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmpassword by remember { mutableStateOf((TextFieldValue(""))) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("login") })
        {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Account",
                modifier = Modifier.size(50.dp)
            )
        }
        OutlinedTextField(value = username, shape = RoundedCornerShape(30.dp), onValueChange = { newText ->
            username = newText
        }, label = { Text("Username") })
        OutlinedTextField(value = email, shape = RoundedCornerShape(30.dp), onValueChange = { newText ->
            email = newText
        }, label = { Text("Email") })
        OutlinedTextField(value = password, shape = RoundedCornerShape(30.dp), onValueChange = { newText ->
            password = newText
        }, label = { Text("Password") })
        ElevatedButton(onClick = {
            var baseUrl = "http://10.0.2.2:1337/api/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RegisterService::class.java)
            val call = retrofit.saveData(RegisterData(email.text, username.text, password.text))
            call.enqueue(object : Callback<LoginRespon> {
                override fun onResponse(
                    call: Call<LoginRespon>,
                    response: Response<LoginRespon>
                ) {
                    print(response.code())
                    if (response.code() == 200) {
                        navController.navigate("pagetwo")
                    } else if (response.code() == 400) {
                        print(response.raw())
                        var toast = Toast.makeText(
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
        }) {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Register")
        }

    }
}