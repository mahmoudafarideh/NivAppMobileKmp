package ir.niv.app.ui.utils

import android.util.Log

actual fun logInfo(key: String, message: Any) {
    Log.d(key, message.toString())
}