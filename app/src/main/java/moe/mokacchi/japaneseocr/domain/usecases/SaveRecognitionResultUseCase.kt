package moe.mokacchi.japaneseocr.domain.usecases

import moe.mokacchi.japaneseocr.domain.contract.IRecognitionResultRepository
import moe.mokacchi.japaneseocr.domain.model.RecognitionResult

class SaveRecognitionResultUseCase(private val repository: IRecognitionResultRepository) {
    suspend fun invoke(result: RecognitionResult): Int {
        return repository.save(result)
    }
}