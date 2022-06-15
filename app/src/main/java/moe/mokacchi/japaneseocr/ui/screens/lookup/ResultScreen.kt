package moe.mokacchi.japaneseocr.ui.screens.lookup

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import moe.mokacchi.japaneseocr.domain.model.OCRResult
import moe.mokacchi.japaneseocr.domain.usecases.GetOCRResultUseCase
import moe.mokacchi.japaneseocr.ui.components.CameraOCRPreview
import moe.mokacchi.japaneseocr.ui.components.OCRResultOverlay
import org.koin.androidx.compose.inject

@Composable
fun ResultScreen(
    resultId: Int?
) {
    val getOCRResultUseCase: GetOCRResultUseCase by inject()
    val imageLoader: ImageLoader by inject()
    val context = LocalContext.current

    var result: OCRResult? by remember { mutableStateOf(null)}

    LaunchedEffect(true) {
        if(resultId != null) {
            result = getOCRResultUseCase.invoke(resultId)
        }
    }

    if(result != null) {
        AsyncImage(
            model = result!!.imagePath,
            imageLoader = imageLoader,
            contentDescription = null
        )
        OCRResultOverlay(result!!, modifier = Modifier.fillMaxSize()) {
            Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
        }
    }
}