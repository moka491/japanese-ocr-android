package moe.mokacchi.japaneseocr.koin

import androidx.camera.view.LifecycleCameraController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { LifecycleCameraController(androidContext()) }
}