package ir.niv.app

import android.app.Application
import ir.niv.app.di.initKoin
import ir.niv.app.domain.core.context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class NivApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        context = this
        initKoin {
            androidLogger()
            androidContext(this@NivApp)
        }
    }
}