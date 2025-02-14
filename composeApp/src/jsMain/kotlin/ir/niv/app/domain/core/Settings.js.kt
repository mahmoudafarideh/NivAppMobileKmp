package ir.niv.app.domain.core

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual val settings: Settings = StorageSettings()