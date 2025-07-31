package com.intel.ciao

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController

import androidx.compose.material3.Text
import com.intel.ciao.HomeScreen
import com.intel.ciao.SecondScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") {LoginScreen(navController)}
        composable("second") { SecondScreen(navController) }
        composable("video_submit") { VideoScreen(navController) }
        composable("video_return") { VideoReturnScreen(navController) }
        composable("submit") { SubmitScreen(navController) }
        composable("return") { ReturnScreen(navController) }
    }
}
