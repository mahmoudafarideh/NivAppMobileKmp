package ir.niv.app.domain.profile.crop

import androidx.compose.ui.graphics.ImageBitmap
import ir.niv.app.ui.profile.crop.decodeBase64ToImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CropRepository {
    private var image: String? = null

    fun setImage(base64: String) {
        image = base64
    }

    suspend fun getImage(): ImageBitmap? {
        return withContext(Dispatchers.Default) {
            image?.decodeBase64ToImageBitmap()
        }
    }

    fun reset() {
        image = null
    }
}