package moe.mokacchi.japaneseocr.logic

import android.content.Context
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Camera(private val context: Context) {
    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context);
    private var cameraProvider: ProcessCameraProvider? = null

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val cameraSelector =
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

    private val imageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(720, 1280))
        .build()

    val preview = Preview.Builder()
        .build()

    fun start(lifecycleOwner: LifecycleOwner, onTextAnalyzed: (FrameResult) -> Unit) {

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            imageAnalysis.setAnalyzer(
                cameraExecutor,
                TextAnalyzer(onTextAnalyzed)
            )

            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner, cameraSelector, imageAnalysis, preview
            )
        }, ContextCompat.getMainExecutor(context))
    }

    fun stop() {
        cameraProvider?.unbindAll()
    }
}


