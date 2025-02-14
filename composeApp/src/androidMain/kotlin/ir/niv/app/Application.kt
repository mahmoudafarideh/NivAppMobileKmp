package ir.niv.app

import android.app.Application
import ir.niv.app.domain.core.context

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}