package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.camera.core.ExperimentalAnalyzer
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
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
import java.io.File
import java.util.concurrent.Executors


@Composable
@ExperimentalAnalyzer
fun CameraScreen(
    navController: NavController,
) {
    var textBlocks: List<Text.TextBlock> by remember { mutableStateOf(emptyList()) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = get<LifecycleCameraController>()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val textRecognizer =
        remember { TextRecognition.getClient(JapaneseTextRecognizerOptions()) }

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
    CameraControls {
        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(
                File("${context.externalCacheDir}${File.separator}${System.currentTimeMillis()}.png")
            ).build()

        cameraController.takePicture(
            outputFileOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    navController.navigate("lookup")
                }

                override fun onError(exception: ImageCaptureException) {
                }
            })
    }
}

