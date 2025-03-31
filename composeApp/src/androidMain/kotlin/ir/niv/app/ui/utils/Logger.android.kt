package ir.niv.app.ui.utils

import android.annotation.SuppressLint
import android.util.Log

@SuppressLint("MemberExtensionConflict")
actual fun logInfo(key: String, message: Any) {
    Log.d(key, message.toString())
}