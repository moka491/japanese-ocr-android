package moe.mokacchi.japaneseocr.ui.screens.camera

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.mokacchi.japaneseocr.domain.model.OCRResult
import moe.mokacchi.japaneseocr.domain.model.RectangleRegion
import moe.mokacchi.japaneseocr.domain.model.TextBlock
import moe.mokacchi.japaneseocr.domain.model.TextLine
import moe.mokacchi.japaneseocr.domain.usecases.SaveOCRResultUseCase
import org.koin.androidx.compose.get

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.util.concurrent.Executors

class CameraScreenViewModel : ViewModel(), KoinComponent {

    val cameraController: LifecycleCameraController by inject()
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private val textRecognizer =
        TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())

    private val saveOCRResultUseCase: SaveOCRResultUseCase by inject()

    var textBlocks: List<Text.TextBlock> by mutableStateOf(emptyList())

    fun bindCameraLifecycle(lifecycleOwner: LifecycleOwner) {
        cameraController.bindToLifecycle(lifecycleOwner)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun setupAnalyzer() {
        cameraController.setImageAnalysisAnalyzer(
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

    fun takePictureAndStoreResult(context: Context, onResultSavedCallback: (Int) -> Unit) {
        takePicture(context) { imageUri ->
            val result = generateOCRResult(imageUri)

            viewModelScope.launch {
                val resultId = saveOCRResultUseCase.invoke(result)
                onResultSavedCallback(resultId)
            }
        }
    }

    private fun takePicture(context: Context, onPictureTaken: (String) -> Unit) {
        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(
                File("${context.externalCacheDir}${File.separator}${System.currentTimeMillis()}.png")
            ).build()

        cameraController.takePicture(
            outputFileOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onPictureTaken(outputFileResults.savedUri.toString())
                }

                override fun onError(exception: ImageCaptureException) {}
            })
    }

    private fun generateOCRResult(imagePath: String): OCRResult {
        return OCRResult(
            imagePath,
            textBlocks.filter { block -> block.boundingBox != null }.map { block ->
                TextBlock(
                    block.text,
                    block.boundingBox.let { box ->
                        RectangleRegion(
                            box!!.top,
                            box.left,
                            box.width(),
                            box.height()
                        )
                    }, block.lines.filter { line -> line.boundingBox != null }.map { line ->
                        TextLine(line.text, line.boundingBox.let { box ->
                            RectangleRegion(
                                box!!.top,
                                box.left,
                                box.width(),
                                box.height()
                            )
                        })
                    })
            })
    }
}