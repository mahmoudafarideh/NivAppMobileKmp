package ir.niv.app.ui.profile.picker

import androidx.compose.runtime.Composable


interface ImagePicker {
    suspend fun pickImage()
}

data class ImageResult(
    val base64: String?
)

@Composable
expect fun rememberImagePicker(result: (String?) -> Unit): ImagePicker

