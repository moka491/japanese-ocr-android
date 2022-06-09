package moe.mokacchi.japaneseocr.domain.model

data class OCRResult(
    val imagePath: String,
    val textBlocks: List<TextBlock>
)