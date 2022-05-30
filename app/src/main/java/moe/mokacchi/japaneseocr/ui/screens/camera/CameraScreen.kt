package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import moe.mokacchi.japaneseocr.Camera
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
    CameraControls(camera.imageCapture)
}
