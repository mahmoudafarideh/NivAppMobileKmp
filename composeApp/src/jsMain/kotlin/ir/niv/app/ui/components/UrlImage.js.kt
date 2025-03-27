package ir.niv.app.ui.components

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory

actual fun PlatformContext.imageLoader(): ImageLoader {
    return ImageLoader.Builder(this)
        .components {
            add(KtorNetworkFetcherFactory())
        }
        .build()
}