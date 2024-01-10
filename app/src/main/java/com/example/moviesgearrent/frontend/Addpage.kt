package com.example.moviesgearrent.frontend

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesgearrent.BottomNavigationAdmin
import java.lang.reflect.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Addpage(navController: NavController, context: Context = LocalContext.current) {

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
                value = "",
                onValueChange = {},
                label = { Text("Nama Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Harga Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Deskripsi Produk") },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            )
            Button(
                onClick = {
                    navController.navigate("HomeAdmin")
                },
                modifier = androidx.compose.ui.Modifier.padding(top = 20.dp)
            ) {
                Text("Add")
            }
        }
    }
}

