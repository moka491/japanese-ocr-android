package moe.mokacchi.japaneseocr.ui.components

import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(controller: CameraController, modifier: Modifier) {
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).also {
                it.controller = controller
            }
        }, modifier
    )
}