package com.intel.ciao

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.intel.ciao.HomeScreen
import com.intel.ciao.SecondScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") {LoginScreen(navController)}
        composable("second") { SecondScreen(navController) }
        composable("photo") { PhotoScreen(navController) }
        composable("submit") { SubmitScreen(navController) }
    }
}
