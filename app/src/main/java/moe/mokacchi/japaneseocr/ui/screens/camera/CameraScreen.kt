package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import moe.mokacchi.japaneseocr.logic.Camera
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraPreview

@Composable
fun CameraScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
    val camera = remember { Camera(context, owner).also { it.startCamera() } }

    CameraPreview(camera.preview, modifier = Modifier.fillMaxSize())
    CameraControls(onCameraButtonClick = { camera.stopCamera() })
}
