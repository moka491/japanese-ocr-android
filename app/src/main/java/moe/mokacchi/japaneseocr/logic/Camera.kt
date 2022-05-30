package moe.mokacchi.japaneseocr.logic

import android.content.Context
import android.util.Rational
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.text.Text
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

    val preview = Preview.Builder().build()

    private val viewPort: ViewPort = ViewPort.Builder(Rational(3,4), preview.targetRotation).setScaleType(ViewPort.FILL_CENTER).build()
    private val useCaseGroup = UseCaseGroup.Builder()
        .setViewPort(viewPort)
        .addUseCase(preview)
        .addUseCase(imageAnalysis)
        .build()


    init {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
        }, ContextCompat.getMainExecutor(context))

    }

    fun startCamera(lifecycleOwner: LifecycleOwner, onTextAnalyzed: (Text) -> Unit) {
        imageAnalysis.setAnalyzer(
            cameraExecutor,
            JapaneseTextAnalyzer(onTextAnalyzed)
        )

        cameraProvider?.unbindAll()
        cameraProvider?.bindToLifecycle(
            lifecycleOwner, cameraSelector, useCaseGroup
        )
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
    }
}


