package moe.mokacchi.japaneseocr.logic

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Size
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import java.io.Serializable

data class FrameResult(
    val frameSize: Size,
    val result: Text
) : Serializable

typealias OnFrameAvailableFn = (FrameResult) -> Unit

class TextAnalyzer(private val onFrameAvailable: OnFrameAvailableFn) :
    ImageAnalysis.Analyzer {

    private val recognizer =
        TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image =
                InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            recognizer.process(image).addOnSuccessListener {

                val size = getRotationAwareImageSize(imageProxy)

                val result = FrameResult(
                    size,
                    it
                )

                onFrameAvailable(result)

            }.addOnFailureListener { e ->
                Log.e("ImageAnalysis", e.message ?: "")

            }.addOnCompleteListener {
                imageProxy.close()

            }
        }
    }

    private fun getRotationAwareImageSize(imageProxy: ImageProxy): Size {
        return when (imageProxy.imageInfo.rotationDegrees) {
            0, 180 -> Size(imageProxy.width.toFloat(), imageProxy.height.toFloat())
            else -> Size(imageProxy.height.toFloat(), imageProxy.width.toFloat())
        }
    }
}