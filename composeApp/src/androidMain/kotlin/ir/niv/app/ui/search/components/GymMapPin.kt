package ir.niv.app.ui.search.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.request.transformations
import coil3.toBitmap
import coil3.transform.CircleCropTransformation
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import ir.niv.app.ui.theme.theme.extendedColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GymMapPin(
    image: String,
    open: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f, true)
                .clip(CircleShape)
                .clickable {
                    onClick()
                }
                .background(NivTheme.colorScheme.primary)
                .padding(2.dp)
        ) {
            UrlImage(
                url = image,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .width(14.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(NivTheme.colorScheme.primary)
                .padding(2.dp)
                .clip(CircleShape)
                .background(
                    if (open) NivTheme.extendedColors.success.color
                    else NivTheme.colorScheme.error
                )
        )
    }
}

@Preview
@Composable
private fun GymMapPinPreview() {
    NivThemePreview {
        Box(modifier = Modifier.size(114.dp)) {
            GymMapPin(
                image = "",
                open = true,
                onClick = {}
            )
        }
    }
}

suspend fun createMarkerBitmapWithBackground(
    context: Context,
    imageUrl: String,
    @DrawableRes backgroundResId: Int,
    widthDp: Int = 62,
    heightDp: Int = 76
): Bitmap? = withContext(Dispatchers.IO) {
    val widthPx = (widthDp * context.resources.displayMetrics.density).toInt()
    val heightPx = (heightDp * context.resources.displayMetrics.density).toInt()

    // Load image using Coil
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false) // must disable for bitmap access
        .size(widthPx, heightPx)
        .transformations(listOf(CircleCropTransformation()))
        .build()

    val result = loader.execute(request)
    val userBitmap = (result as? SuccessResult)?.image?.toBitmap(widthPx, widthPx) ?: return@withContext null
    val backgroundDrawable = ContextCompat.getDrawable(context, backgroundResId) ?: return@withContext null
    val backgroundBitmap = createBitmap(widthPx, heightPx)
    val canvas = Canvas(backgroundBitmap)
    backgroundDrawable.setBounds(0, 0, canvas.width, canvas.height)
    backgroundDrawable.draw(canvas)

    val inset = 8.dpToPx(context)
    val imageRect = Rect(inset, inset, widthPx - inset, widthPx - inset)
    canvas.drawBitmap(userBitmap, null, imageRect, null)

    backgroundBitmap
}

fun Int.dpToPx(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()