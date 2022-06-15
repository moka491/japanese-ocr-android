package moe.mokacchi.japaneseocr.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import moe.mokacchi.japaneseocr.ui.screens.camera.CameraScreen
import moe.mokacchi.japaneseocr.ui.screens.lookup.ResultScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "camera") {
        composable("camera") {
            CameraScreen(
                navController
            )
        }
        composable(
            "result/{resultId}",
            arguments = listOf(navArgument("resultId") { type = NavType.IntType })
        ) { entry ->
            ResultScreen(
                entry.arguments?.getInt("resultId")
            )
        }
    }
}