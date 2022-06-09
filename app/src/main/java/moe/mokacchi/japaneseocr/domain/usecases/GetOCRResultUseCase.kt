package moe.mokacchi.japaneseocr.domain.usecases

import moe.mokacchi.japaneseocr.domain.contract.OCRResultRepository
import moe.mokacchi.japaneseocr.domain.model.OCRResult

class GetOCRResultUseCase(private val repository: OCRResultRepository) {
     suspend fun invoke(id: Int): OCRResult? {
        return repository.getOne(id)
    }
}