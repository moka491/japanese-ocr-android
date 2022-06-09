package moe.mokacchi.japaneseocr.data

import moe.mokacchi.japaneseocr.domain.contract.OCRResultRepository
import moe.mokacchi.japaneseocr.domain.model.OCRResult

class OCRResultRepositoryImpl: OCRResultRepository {
    private val cache = mutableListOf<OCRResult>()

    override suspend fun getOne(id: Int): OCRResult? {
        return cache.getOrNull(id)
    }

    override suspend fun save(result: OCRResult): Int {
        cache.add(result)
        return cache.size - 1
    }
}