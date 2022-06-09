package moe.mokacchi.japaneseocr.domain.usecases

import moe.mokacchi.japaneseocr.domain.contract.OCRResultRepository
import moe.mokacchi.japaneseocr.domain.model.OCRResult

class SaveOCRResultUseCase(private val repository: OCRResultRepository) {
     suspend fun invoke(result: OCRResult): Int {
        return repository.save(result)
    }
}