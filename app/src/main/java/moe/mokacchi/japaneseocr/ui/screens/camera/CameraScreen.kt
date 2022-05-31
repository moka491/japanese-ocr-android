package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import moe.mokacchi.japaneseocr.logic.Camera
import moe.mokacchi.japaneseocr.logic.FrameResult
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraOCRBoundaryOverlay
import moe.mokacchi.japaneseocr.ui.components.CameraPreview
import org.koin.androidx.compose.get

@Composable
fun CameraScreen(
    navController: NavController,
) {
    var frameResult: FrameResult? by remember { mutableStateOf(null)}

    val lifecycleOwner = LocalLifecycleOwner.current
    val camera = get<Camera>()

    LaunchedEffect(true) {
        camera.start(lifecycleOwner, onTextAnalyzed = { result -> frameResult = result })
    }

    CameraPreview(camera.preview, modifier = Modifier.fillMaxSize())
    frameResult?.let { CameraOCRBoundaryOverlay(it, modifier = Modifier.fillMaxSize()) }
    CameraControls {  }
}
