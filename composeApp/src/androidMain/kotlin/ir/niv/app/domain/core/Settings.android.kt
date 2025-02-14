package ir.niv.app.domain.core

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

lateinit var context: Context
actual val settings: Settings = SharedPreferencesSettings
    .Factory(context)
    .create(null)