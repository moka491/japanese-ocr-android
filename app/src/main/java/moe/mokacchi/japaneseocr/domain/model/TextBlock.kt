package moe.mokacchi.japaneseocr.domain.model

data class TextBlock(
    val text: String,
    val location: RectangleRegion,
    val lines: List<TextLine>
)

