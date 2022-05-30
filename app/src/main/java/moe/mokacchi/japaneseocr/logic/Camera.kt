package moe.mokacchi.japaneseocr.logic

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Camera(context: Context) {
    private var cameraProvider: ProcessCameraProvider? = null

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val cameraSelector =
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

    private val imageAnalysis = ImageAnalysis.Builder()
        .build()

    val preview = Preview.Builder()
        .build()

    init {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(context))
    }

    fun start(lifecycleOwner: LifecycleOwner, onTextAnalyzed: (FrameResult) -> Unit) {
        imageAnalysis.setAnalyzer(
            cameraExecutor,
            TextAnalyzer(onTextAnalyzed)
        )

        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(
            lifecycleOwner, cameraSelector, imageAnalysis, preview
        )
    }

    fun stop() {
        cameraProvider?.unbindAll()
    }
}


