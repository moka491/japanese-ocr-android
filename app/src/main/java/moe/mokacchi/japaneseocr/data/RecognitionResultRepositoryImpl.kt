package moe.mokacchi.japaneseocr.data

import moe.mokacchi.japaneseocr.domain.contract.IRecognitionResultRepository
import moe.mokacchi.japaneseocr.domain.model.RecognitionResult

class RecognitionResultRepositoryImpl: IRecognitionResultRepository {
    override suspend fun getOne(id: Int): RecognitionResult {
        TODO("Not yet implemented")
    }

    override suspend fun save(result: RecognitionResult): Int {
        TODO("Not yet implemented")
    }
}