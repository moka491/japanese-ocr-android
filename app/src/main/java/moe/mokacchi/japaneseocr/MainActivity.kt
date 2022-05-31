package moe.mokacchi.japaneseocr

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import moe.mokacchi.japaneseocr.ui.Navigation
import moe.mokacchi.japaneseocr.ui.theme.JapaneseOCRTheme

class MainActivity : ComponentActivity() {

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            if (granted) {
                setViewContent()
            } else {
                Log.d("MainActivity", permissions.entries.toString())
                finishAndRemoveTask();
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionRequest.launch(PERMISSIONS)
    }

    private fun setViewContent() {
        setContent {
            JapaneseOCRTheme {
                Navigation()
            }
        }
    }

    companion object {
        private val PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()

    }
}
