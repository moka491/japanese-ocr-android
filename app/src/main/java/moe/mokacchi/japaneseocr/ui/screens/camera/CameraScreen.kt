package moe.mokacchi.japaneseocr.ui.screens.camera

import android.annotation.SuppressLint
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.ImageCaptureException
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.mokacchi.japaneseocr.ui.components.CameraControls
import moe.mokacchi.japaneseocr.ui.components.CameraOCRBoundaryOverlay
import moe.mokacchi.japaneseocr.ui.components.CameraPreview
import org.koin.androidx.compose.get
import org.koin.androidx.compose.inject
import java.io.File
import java.util.concurrent.Executors

@Composable
@SuppressLint("UnsafeOptInUsageError")
fun CameraScreen(
    navController: NavController,
) {
    val viewModel: CameraScreenViewModel by inject()

    var textBlocks: List<Text.TextBlock> by remember { mutableStateOf(emptyList()) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = get<LifecycleCameraController>()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val textRecognizer =
        remember { TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build()) }
    val coroutineScope = rememberCoroutineScope()

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
                    coroutineScope.launch {

                        val result = viewModel.generateOCRResult(
                            outputFileResults.savedUri.toString(),
                            textBlocks
                        )

                        val resultId = viewModel.saveResult(result)

                        withContext(Dispatchers.Main) {
                            navController.navigate("result/$resultId")
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {}
            })
    }
}

