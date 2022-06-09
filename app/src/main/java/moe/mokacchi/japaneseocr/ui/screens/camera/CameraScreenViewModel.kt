package moe.mokacchi.japaneseocr.ui.screens.camera

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.text.Text
import moe.mokacchi.japaneseocr.domain.model.OCRResult
import moe.mokacchi.japaneseocr.domain.model.RectangleRegion
import moe.mokacchi.japaneseocr.domain.model.TextBlock
import moe.mokacchi.japaneseocr.domain.model.TextLine
import moe.mokacchi.japaneseocr.domain.usecases.SaveOCRResultUseCase

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CameraScreenViewModel : ViewModel(), KoinComponent {
    private val saveOCRResultUseCase: SaveOCRResultUseCase by inject()

    fun generateOCRResult(imagePath: String, textBlocks: List<Text.TextBlock>): OCRResult {
        return OCRResult(
            imagePath,
            textBlocks.filter { block -> block.boundingBox != null }.map { block ->
                TextBlock(
                    block.text,
                    block.boundingBox.let { box ->
                        RectangleRegion(
                            box!!.top,
                            box.left,
                            box.width(),
                            box.height()
                        )
                    }, block.lines.filter { line -> line.boundingBox != null }.map { line ->
                        TextLine(line.text, line.boundingBox.let { box ->
                            RectangleRegion(
                                box!!.top,
                                box.left,
                                box.width(),
                                box.height()
                            )
                        })
                    })
            })
    }

    suspend fun saveResult(ocrResult: OCRResult): Int {
        return saveOCRResultUseCase.invoke(ocrResult)
    }

}