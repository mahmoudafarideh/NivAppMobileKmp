package ir.niv.app.ui.profile.crop

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.util.decodeBase64Bytes

actual fun String.decodeBase64ToImageBitmap(): ImageBitmap? {
    val cleanBase64 = substringAfter("base64,")
    val bytes = cleanBase64.decodeBase64Bytes()
    return org.jetbrains.skia.Image.makeFromEncoded(bytes)
        .toComposeImageBitmap()
}