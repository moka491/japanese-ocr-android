package moe.mokacchi.japaneseocr.koin

import moe.mokacchi.japaneseocr.logic.Camera
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { Camera(androidContext()) }
}