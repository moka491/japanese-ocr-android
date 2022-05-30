package moe.mokacchi.japaneseocr.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import moe.mokacchi.japaneseocr.ui.screens.camera.CameraScreen
import moe.mokacchi.japaneseocr.ui.screens.recognition.RecognitionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context: Context = LocalContext.current;

    NavHost(navController = navController, startDestination = "camera") {
        composable("camera") {
            CameraScreen(
                navController
            )
        }
        composable("recognition") {
            RecognitionScreen(
                navController
            )
        }
    }
}