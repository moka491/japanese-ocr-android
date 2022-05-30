package moe.mokacchi.japaneseocr.logic

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions

class JapaneseTextAnalyzer(private val processedFrameCallback: (Text) -> Unit) :
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
                processedFrameCallback(it)
            }.addOnFailureListener { e ->
                Log.e("ImageAnalysis", e.message ?: "")
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}