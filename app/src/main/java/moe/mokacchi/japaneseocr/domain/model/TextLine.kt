package moe.mokacchi.japaneseocr.domain.model

data class TextLine(
    val text: String,
    val location: RectangleRegion
)