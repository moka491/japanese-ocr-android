package moe.mokacchi.japaneseocr.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import moe.mokacchi.japaneseocr.R

@Composable
fun CameraControls(onCameraButtonClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Button(onClick = onCameraButtonClick) {
            Image(painterResource(id = R.drawable.ic_baseline_camera_alt_24), contentDescription = "Take a photo")
        }
    }
}