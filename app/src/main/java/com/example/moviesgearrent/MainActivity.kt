package com.example.moviesgearrent

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.compose.AppTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesgearrent.data.LoginData
import com.example.moviesgearrent.frontend.CreateUser
import com.example.moviesgearrent.frontend.Detailpage
import com.example.moviesgearrent.frontend.EditUser
import com.example.moviesgearrent.frontend.Homepage
import com.example.moviesgearrent.frontend.StatusPage
import com.example.moviesgearrent.respon.LoginRespon
import com.example.moviesgearrent.service.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val sharedPreferences: SharedPreferences =
                LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val navController = rememberNavController()

            var startDestination: String
            var jwt = sharedPreferences.getString("jwt", "")
            if (jwt.equals("")) {
                startDestination = "login"
            } else {
                startDestination = "homepage"
            }
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                    ) {
                        composable(route = "login") {
                            Login(navController)
                        }
                        composable(route = "homepage") {
                            Homepage(navController)
                        }
                        composable("detailpage/{produkId}/{nama_produk}/{desc_produk}/{harga}/{status}") { backStackEntry ->
                            Detailpage(navController,
                                backStackEntry.arguments?.getString("produklId"),
                                backStackEntry.arguments?.getString("nama_produk"),
                                backStackEntry.arguments?.getString("desc_produk"),
                                backStackEntry.arguments?.getString("harga"),
                                backStackEntry.arguments?.getString("status")
                            )
                        }
                        composable(route = "createuser") {
                            CreateUser(navController)
                        }
                        composable(route = "StatusPage") {
                            StatusPage(
                                navController,
                                id = it.arguments?.getString("id"),
                            )
                        }
                        composable(
                            route = "edituser/{userid}/{username}",
                        ) { backStackEntry ->
                            EditUser(
                                navController,
                                backStackEntry.arguments?.getString("userid"),
                                backStackEntry.arguments?.getString("username")
                            )
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, context: Context = LocalContext.current) {

    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    var jwt by remember { mutableStateOf("") }
    val eyeOpen = painterResource(id = R.drawable.visible)
    val eyeClose = painterResource(id = R.drawable.hidden)
    var passwordVisibility by remember { mutableStateOf(false) }

    jwt = preferencesManager.getData("jwt")
    Column {
        Image(
            painter = painterResource(id = R.drawable.mgrlogo),
            contentDescription = "Logo MGR",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
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
            label = { Text("Username") })
        OutlinedTextField(
            value = password,
            shape = RoundedCornerShape(30.dp),
            onValueChange = { newText ->
                password = newText
            },
            label = { Text("Password") },
            visualTransformation =
            if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val icon = if (passwordVisibility) eyeOpen else eyeClose
                    Image(
                        painter = icon,
                        contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        Row {
            Text(text = "Belum Punya Akun?", fontSize = 12.sp)
            Spacer(modifier = Modifier.padding(1.dp, 30.dp))
            ClickableText(text = AnnotatedString("Register"),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Red
                ), onClick = { navController.navigate("createuser") })

        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {

            Button(onClick = {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(LoginService::class.java)
                val call = retrofit.getData(LoginData(username.text, password.text))
                call.enqueue(object : Callback<LoginRespon> {
                    override fun onResponse(
                        call: Call<LoginRespon>,
                        response: Response<LoginRespon>
                    ) {
                        if (response.isSuccessful) {
                            preferencesManager.saveData("jwt", response.body()?.jwt!!)
                            if(response.body()?.user?.role!! == "user"){
                                navController.navigate("Homepage")
                            }else{
                                navController.navigate("admin")
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "salah ya bang",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginRespon>, t: Throwable) {
                        Toast.makeText(
                            context,
                            "Error: salah ya bang",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                )
            }
            ) {
                Text(
                    text = "LOGIN",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    ),
                    modifier = Modifier.padding(horizontal = 50.dp, vertical = 8.dp)
                )
            }
        }

    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    NavigationBar {
        val bottomNavigation = listOf(
            BottomNavItem(
                label = "Home",
                icon = Icons.Default.Home,
                route = "homepage"
            ),
            BottomNavItem(
                label = "Notifikasi",
                icon = Icons.Default.AddAlert,
                route = ""
            ),
            BottomNavItem(
                label = "History",
                icon = Icons.Default.AccessTimeFilled,
                route = ""
            ),
            BottomNavItem(
                label = "Profile",
                icon = Icons.Default.AccountCircle,
                route = ""
            )
        )
        bottomNavigation.map {
            NavigationBarItem(
                selected = navController.currentDestination?.route == it.route,
                onClick = { navController.navigate(it.route) },
                icon = { Icon(imageVector = it.icon, contentDescription = it.label, tint = MaterialTheme.colorScheme.primary) },
                label = {Text(text = it.label)},
            )
        }
    }
}
data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
