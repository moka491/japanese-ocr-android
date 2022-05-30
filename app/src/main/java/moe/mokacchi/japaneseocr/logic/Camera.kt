package moe.mokacchi.japaneseocr.logic

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class Camera(private val context: Context, private val lifecycleOwner: LifecycleOwner) {
    private var cameraProvider: ProcessCameraProvider? = null

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val cameraSelector =
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

    val imageCapture =
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()

    val analyzer = ImageAnalysis.Builder()
        .build()

    val preview = Preview.Builder().build()

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            analyzer.setAnalyzer(cameraExecutor, JapaneseTextAnalyzer { text -> Log.d("ImageAnalysis", text.text) })

            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner, cameraSelector, analyzer, preview
            )
        }, ContextCompat.getMainExecutor(context))
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
    }


}


