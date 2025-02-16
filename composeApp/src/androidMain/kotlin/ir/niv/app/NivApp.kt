package ir.niv.app

import android.app.Application
import android.util.Log
import ir.niv.app.di.initKoin
import ir.niv.app.ui.splash.graph.SplashRoute
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class NivApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@NivApp)
        }
    }
}