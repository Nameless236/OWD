package com.example.owd

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.owd.navigation.OwdNavHost

@Composable
fun OwdApp(navController: NavHostController = rememberNavController()) {
    OwdNavHost(navController = navController, modifier = Modifier)
}
