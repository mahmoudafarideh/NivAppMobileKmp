package ir.niv.app.ui.profile.picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream


private class AndroidImagePicker(private val launcher: ManagedActivityResultLauncher<String, Uri?>) :
    ImagePicker {
    override suspend fun pickImage() {
        launcher.launch("image/*")
    }

}

@Composable
actual fun rememberImagePicker(result: (String?) -> Unit): ImagePicker {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        val imageBitmap = it?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap?.let {
                    val outputStream = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    val byteArray = outputStream.toByteArray()
                    Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
            } catch (_: Exception) {
                null
            }
        }
        result(imageBitmap)
    }

    return remember {
        AndroidImagePicker(launcher)
    }
}