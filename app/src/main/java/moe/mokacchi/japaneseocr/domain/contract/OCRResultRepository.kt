package moe.mokacchi.japaneseocr.domain.contract

import moe.mokacchi.japaneseocr.domain.model.OCRResult

interface OCRResultRepository {
    suspend fun getOne(id: Int): OCRResult?

    suspend fun save(result: OCRResult): Int
}