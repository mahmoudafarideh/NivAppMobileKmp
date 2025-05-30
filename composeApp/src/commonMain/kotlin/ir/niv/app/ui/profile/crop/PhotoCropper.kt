package ir.niv.app.ui.profile.crop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.theme.NivThemePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

expect fun String.decodeBase64ToImageBitmap(): ImageBitmap?

val Float.pxToDp @Composable get() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun SquareCropper(
    imageWidth: Int,
    imageHeight: Int
) {
//    val imageViewModel: CropViewModel = koinViewModel()
//    val imageBitmap by imageViewModel.state.collectAsStateWithLifecycle()
//    if (imageBitmap == null) return
//    val cropSizePx = with(LocalDensity.current) { cropSize.toPx() }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
//                    offsetX = offsetX.coerceIn(0f, imageBitmap!!.width - cropSizePx)
//                    offsetY = offsetY.coerceIn(0f, imageBitmap!!.height - cropSizePx)
                }
            }
    ) {
        val rectArea = remember {
            mutableStateOf<Pair<Offset, Size>?>(null)
        }
        val padding = 24.dp

        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Ltr,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                rectArea.value?.let { (offset, size) ->
                    Spacer(
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .then(
                                when {
                                    imageWidth > imageHeight -> {
                                        Modifier
                                            .height(size.height.pxToDp)
                                            .width(imageWidth.times(size.height.div(imageHeight.toFloat())).pxToDp)
                                    }
                                    else -> {
                                        Modifier
                                            .width(size.width.pxToDp)
                                            .height(imageHeight.times(size.width.div(imageWidth.toFloat())).pxToDp)
                                    }
                                }
                            )
                            .background(Color.Red)

                    )
                }
            }
        }


        val path = remember { Path() }
        val minusPath = remember { Path() }

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val padding = padding.toPx()
            val rectSize = size.width.coerceAtMost(size.height)
            val rectOffset = Offset(
                x = ((size.width.minus(rectSize)).div(2f)).plus(padding),
                y = ((size.height.minus(rectSize)).div(2f)).plus(padding),
            )

            if (rectArea.value == null) {
                rectArea.value = rectOffset to Size(
                    rectSize.minus(padding.times(2)),
                    rectSize.minus(padding.times(2))
                )
            }

            path.reset()
            path.addRect(
                Rect(0f, 0f, size.width, size.height)
            )
            minusPath.reset()
            minusPath.moveTo(rectOffset.x, rectOffset.y)
            minusPath.lineTo(size.width.minus(rectOffset.x), rectOffset.y)
            minusPath.lineTo(size.width.minus(rectOffset.x), size.height.minus(rectOffset.y))
            minusPath.lineTo(rectOffset.x, size.height.minus(rectOffset.y))
            minusPath.lineTo(rectOffset.x, rectOffset.y)
            minusPath.close()

            drawPath(
                color = Color.Black.copy(alpha = 0.4f),
                path = Path.combine(PathOperation.Difference, path, minusPath)
            )

        }

        Button(
            onClick = {
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text("Crop")
        }
    }
}

@Preview
@Composable
private fun PhotoCropperPreview() {
    NivThemePreview {
        SquareCropper(
            1080,
            1920,
        )
    }
}