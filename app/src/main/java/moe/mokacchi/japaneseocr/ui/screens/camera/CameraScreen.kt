package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.text.Text
import moe.mokacchi.japaneseocr.logic.Camera
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraOCRBoundaryOverlay
import moe.mokacchi.japaneseocr.ui.components.CameraPreview
import org.koin.androidx.compose.get

@Composable
fun CameraScreen(
    navController: NavController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val camera = get<Camera>()

    var blocks by remember { mutableStateOf(emptyList<Text.TextBlock>())}

    CameraPreview(camera.preview, modifier = Modifier.fillMaxSize())
    CameraOCRBoundaryOverlay(blocks, modifier = Modifier.fillMaxSize())
    CameraControls { camera.startCamera(lifecycleOwner, { blocks = it.textBlocks }) }
}
