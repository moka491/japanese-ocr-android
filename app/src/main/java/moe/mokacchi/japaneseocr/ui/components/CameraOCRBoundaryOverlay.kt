package moe.mokacchi.japaneseocr.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.google.mlkit.vision.text.Text

@Composable
fun CameraOCRBoundaryOverlay(textBlocks: List<Text.TextBlock>, modifier: Modifier) {


    Canvas(modifier) {
        textBlocks.forEach { block ->
            block.lines
                .filter { line -> line.boundingBox != null }
                .forEach { line ->
                    val bounds = line.boundingBox!!

                    drawRect(
                        Color.Red,
                        topLeft = Offset(bounds.left.toFloat(), bounds.top.toFloat()),
                        size = Size(bounds.width().toFloat(), bounds.height().toFloat()),
                        alpha = 0.6f,
                        style = Stroke(5f)
                    )
                }
        }
    }
}
