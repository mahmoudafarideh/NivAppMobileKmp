package ir.niv.app.ui.components

import coil3.ImageLoader
import coil3.PlatformContext

actual fun PlatformContext.imageLoader(): ImageLoader {
    return ImageLoader.Builder(this).build()
}