package moe.mokacchi.japaneseocr.domain.model

data class TextBlock(
    val text: String,
    val location: RectangleRegion
)

data class RectangleRegion(
    val top: Int,
    val left: Int,
    val width: Int,
    val height: Int,
)