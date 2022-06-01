package moe.mokacchi.japaneseocr.domain.contract

import moe.mokacchi.japaneseocr.domain.model.RecognitionResult

interface IRecognitionResultRepository {

    suspend fun getOne(id: Int): RecognitionResult

    suspend fun save(result: RecognitionResult): Int
}