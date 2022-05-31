package moe.mokacchi.japaneseocr.ui.screens.camera

import android.annotation.SuppressLint
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraOCRBoundaryOverlay
import moe.mokacchi.japaneseocr.ui.components.CameraPreview
import org.koin.androidx.compose.get
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraScreen(
    navController: NavController,
) {
    var textBlocks: List<Text.TextBlock> by remember { mutableStateOf(emptyList()) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = get<LifecycleCameraController>()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val textRecognizer =
        remember { TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build()) }


    LaunchedEffect(true) {
        with(cameraController) {
            bindToLifecycle(lifecycleOwner)
            setImageAnalysisAnalyzer(
                cameraExecutor,
                MlKitAnalyzer(
                    listOf(textRecognizer),
                    COORDINATE_SYSTEM_VIEW_REFERENCED,
                    cameraExecutor
                ) {
                    val res = it.getValue(textRecognizer)

                    if (res != null) {
                        textBlocks = res.textBlocks

                    }
                })
        }

    }

    CameraPreview(cameraController, modifier = Modifier.fillMaxSize())
    CameraOCRBoundaryOverlay(textBlocks, modifier = Modifier.fillMaxSize())
    CameraControls { }
}
