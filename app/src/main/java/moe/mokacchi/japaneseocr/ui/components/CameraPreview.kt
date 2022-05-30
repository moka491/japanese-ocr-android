package moe.mokacchi.japaneseocr.ui.components

import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(preview: Preview, modifier: Modifier) {
    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx).also {
                preview.setSurfaceProvider(it.surfaceProvider)
            }
            previewView
        }, modifier
    )
}