package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraOCRBoundaryOverlay
import moe.mokacchi.japaneseocr.ui.components.CameraPreview
import org.koin.androidx.compose.inject

@Composable
fun CameraScreen(
    navController: NavController,
) {
    val viewModel: CameraScreenViewModel by inject()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(true) {
        with(viewModel) {
            bindCameraLifecycle(lifecycleOwner)
            setupAnalyzer()
        }
    }

    CameraPreview(viewModel.cameraController, modifier = Modifier.fillMaxSize())
    CameraOCRBoundaryOverlay(viewModel.textBlocks, modifier = Modifier.fillMaxSize())
    CameraControls {
        viewModel.takePictureAndStoreResult(context) { resultId ->
            navController.navigate("result/$resultId")
        }
    }
}

