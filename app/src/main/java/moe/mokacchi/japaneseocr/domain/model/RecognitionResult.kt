package moe.mokacchi.japaneseocr.domain.model

data class RecognitionResult(
    val imagePath: String,
    val recognizedBlocks: List<TextBlock>
)