package ir.niv.app.ui.utils

import android.util.Log

actual fun logInfo(key: String, message: String) {
    Log.d(key, message)
}