package moe.mokacchi.japaneseocr.ui.screens.lookup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import moe.mokacchi.japaneseocr.domain.model.OCRResult
import moe.mokacchi.japaneseocr.domain.usecases.GetOCRResultUseCase
import moe.mokacchi.japaneseocr.ui.screens.camera.CameraScreenViewModel
import org.koin.androidx.compose.inject
import org.koin.core.component.inject

@Composable
fun LookupScreen(
    navController: NavController,
    resultId: Int?
) {
    val getOCRResultUseCase: GetOCRResultUseCase by inject()

    var result: OCRResult? by remember { mutableStateOf(null)}

    LaunchedEffect(true) {
        if(resultId != null) {
            result = getOCRResultUseCase.invoke(resultId)
        }
    }

    if(result != null) {
        Column {
            Text("Image:", fontSize = 20.sp)
            Text(result!!.imagePath)
            Text("Text:", fontSize = 20.sp)
            Text(result!!.textBlocks.joinToString("\n\n") { block -> block.text })
        }
    }
}