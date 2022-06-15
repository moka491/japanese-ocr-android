package moe.mokacchi.japaneseocr.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import moe.mokacchi.japaneseocr.domain.model.OCRResult
import moe.mokacchi.japaneseocr.domain.model.TextBlock

@Composable
fun OCRResultOverlay(result: OCRResult, modifier: Modifier, onBlockClick: (TextBlock) -> Unit = {}) {
    Canvas(modifier
        .pointerInput(Unit) {
            detectTapGestures() { offset ->
                val block = result.textBlocks.find { it.contains(offset.x, offset.y) }

                if(block != null) {
                    onBlockClick(block)
                }
            }
        }) {
        result.textBlocks.forEach { block ->
            val bounds = block.location

            drawRoundRect(
                color = Color.White,
                topLeft = Offset(bounds.left.toFloat(), bounds.top.toFloat()),
                size = Size(bounds.width.toFloat(), bounds.height.toFloat()),
                alpha = 0.6f,
                cornerRadius = CornerRadius(8f, 8f),

                )
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(bounds.left.toFloat(), bounds.top.toFloat()),
                size = Size(bounds.width.toFloat(), bounds.height.toFloat()),
                cornerRadius = CornerRadius(8f, 8f),
                style = Stroke(5f)
            )
        }
    }
}

fun TextBlock.contains(x: Float, y: Float): Boolean {
    return x >= this.location.left && x <= this.location.left + this.location.width && y >= this.location.top && y <= this.location.top + this.location.height
}
