package moe.mokacchi.japaneseocr.domain.usecases

import moe.mokacchi.japaneseocr.domain.contract.IRecognitionResultRepository
import moe.mokacchi.japaneseocr.domain.model.RecognitionResult

class GetRecognitionResultUseCase(private val repository: IRecognitionResultRepository) {
    suspend fun invoke(id: Int): RecognitionResult {
        return repository.getOne(id)
    }
}