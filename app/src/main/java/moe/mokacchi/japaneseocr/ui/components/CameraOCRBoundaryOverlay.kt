package moe.mokacchi.japaneseocr.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import moe.mokacchi.japaneseocr.logic.FrameResult

@Composable
fun CameraOCRBoundaryOverlay(ocrResult: FrameResult, modifier: Modifier) {

    val frameSize =
        calculateFrameSize(ocrResult.frameWidth, ocrResult.frameHeight, ocrResult.frameRotation)

    Canvas(modifier) {
        val scale = calculateCanvasScale(size, frameSize, ocrResult.frameRotation)

        scale(scale) {
            translate(
                left = (size.width / 2) - (frameSize.width / 2),
                top = (size.height / 2) - (frameSize.height / 2)
            ) {

                ocrResult.result.textBlocks.forEach { block ->
                    block.lines.forEach { line ->
                        val bounds = line.boundingBox ?: return@forEach

                        drawRect(
                            Color.Red,
                            topLeft = Offset(bounds.left.toFloat(), bounds.top.toFloat()),
                            size = Size(bounds.width().toFloat(), bounds.height().toFloat()),
                            alpha = 0.4f
                        )
                    }
                }
            }
        }
    }
}

internal fun calculateFrameSize(width: Int, height: Int, rotation: Int): Size {
    return when (rotation) {
        0, 180 -> Size(width.toFloat(), height.toFloat())
        else -> Size(height.toFloat(), width.toFloat())
    }
}

internal fun calculateCanvasScale(canvasSize: Size, frameSize: Size, rotation: Int): Float {
    return when (rotation) {
        0, 180 -> canvasSize.width / frameSize.width
        else -> canvasSize.height / frameSize.height
    }
}