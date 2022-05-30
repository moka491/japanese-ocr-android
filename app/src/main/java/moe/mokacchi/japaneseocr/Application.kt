package moe.mokacchi.japaneseocr

import android.app.Application
import moe.mokacchi.japaneseocr.koin.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@Application)
            modules(coreModule)
        }
    }
}