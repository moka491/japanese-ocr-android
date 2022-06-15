package moe.mokacchi.japaneseocr.koin

import androidx.camera.view.LifecycleCameraController
import coil.ImageLoader
import moe.mokacchi.japaneseocr.data.OCRResultRepositoryImpl
import moe.mokacchi.japaneseocr.domain.contract.OCRResultRepository
import moe.mokacchi.japaneseocr.domain.usecases.GetOCRResultUseCase
import moe.mokacchi.japaneseocr.domain.usecases.SaveOCRResultUseCase
import moe.mokacchi.japaneseocr.ui.screens.camera.CameraScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    single { LifecycleCameraController(androidContext()) }
    single { ImageLoader.Builder(androidContext()).build() }

    single<OCRResultRepository> { OCRResultRepositoryImpl() }

    factory { GetOCRResultUseCase(get()) }
    factory { SaveOCRResultUseCase(get()) }

    viewModel { CameraScreenViewModel() }
}