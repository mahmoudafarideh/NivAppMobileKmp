package ir.niv.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import com.valentinilk.shimmer.shimmer
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.Failed
import ir.niv.app.ui.core.Fetching
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.Retrieved

@Composable
fun UrlImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    failedContent: (@Composable () -> Unit)? = null,
) {
    val context = LocalPlatformContext.current
    val imageLoader: ImageLoader = remember {
        context.imageLoader()
    }
    var imageState: DeferredData<Painter> by remember { mutableStateOf(ReadyToFetch) }
    rememberAsyncImagePainter(
        model = url,
        imageLoader = imageLoader,
        onError = {
            imageState = Failed
        },
        onLoading = {
            imageState = Fetching
        },
        onSuccess = {
            imageState = Retrieved(it.painter)
        }
    )
    Box(modifier = modifier) {
        when (imageState) {
            Failed -> {
                failedContent?.invoke()
            }

            Fetching -> {
                Spacer(modifier = Modifier.fillMaxSize().shimmer())
            }

            is Retrieved<*> -> {
                imageState.data?.let {
                    Image(
                        painter = it,
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            else -> {}
        }
    }
}

expect fun PlatformContext.imageLoader(): ImageLoader