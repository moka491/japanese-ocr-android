package moe.mokacchi.japaneseocr.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import moe.mokacchi.japaneseocr.ui.screens.camera.CameraScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "camera") {
        composable("camera") {
            CameraScreen(
                navController
            )
        }
    }
}