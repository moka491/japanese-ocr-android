package moe.mokacchi.japaneseocr

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner


class Camera(private val context: Context, private val lifecycleOwner: LifecycleOwner) {
    private var cameraProvider: ProcessCameraProvider? = null

    private val cameraSelector =
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

    val imageCapture =
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()

    val preview = Preview.Builder().build()

    fun startCamera() {
        ProcessCameraProvider.getInstance(context).also {
            it.addListener({
                cameraProvider = it.get()

                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner, cameraSelector, imageCapture, preview
                )

            }, ContextCompat.getMainExecutor(context))
        }
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
    }
}


