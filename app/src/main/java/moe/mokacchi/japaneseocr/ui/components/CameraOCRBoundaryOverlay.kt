package moe.mokacchi.japaneseocr.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import moe.mokacchi.japaneseocr.logic.FrameResult

@Composable
fun CameraOCRBoundaryOverlay(ocrResult: FrameResult, modifier: Modifier) {

    val frameSize = ocrResult.frameSize

    Canvas(modifier) {
        val scale = calculateCanvasScale(size, frameSize)

        scale(scale) {
            translate(
                left = (size.width / 2) - (frameSize.width / 2),
                top = (size.height / 2) - (frameSize.height / 2)
            ) {

                ocrResult.result.textBlocks.forEach { block ->
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
    }
}


internal fun calculateCanvasScale(canvasSize: Size, frameSize: Size): Float {
    return if (canvasSize.height >= canvasSize.width) {
        canvasSize.height / frameSize.height
    } else {
        canvasSize.width / frameSize.width
    }
}