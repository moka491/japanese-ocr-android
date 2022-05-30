package moe.mokacchi.japaneseocr.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.google.mlkit.vision.text.Text

@Composable
fun CameraOCRBoundaryOverlay(ocrTextBlocks: List<Text.TextBlock>, modifier: Modifier) {
    Canvas(modifier) {

        fun scaleWidth(px: Int): Float {
            return (px.toFloat() / 480f) * size.width
        }

        fun scaleHeight(px: Int): Float {
            return (px.toFloat() / 640f) * size.height
        }

        ocrTextBlocks.forEach {
            val bounds = it.boundingBox ?: return@forEach

            Log.d("ImageAnalysis", size.toString())


            drawRect(
                Color.Red,
                topLeft = Offset(scaleWidth(bounds.left), scaleHeight(bounds.top)),
                size = Size(scaleWidth(bounds.width()), scaleHeight(bounds.height()))
            )
        }
    }
}