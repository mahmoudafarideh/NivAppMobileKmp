package ir.niv.app.ui.profile.picker

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader

private class JsImagePicker(
    private val callback: (String?) -> Unit
) : ImagePicker {

    override suspend fun pickImage() {
        val input = document.createElement("input") as HTMLInputElement
        input.type = "file"
        input.accept = "image/*"

        input.onchange = {
            val file = input.files?.item(0)
            if (file != null) {
                val reader = FileReader()
                reader.onload = {
                    val base64 = reader.result as? String
                    callback(base64)
                }
                reader.readAsDataURL(file)
            } else {
                callback(null)
            }
        }

        input.click()
    }
}

@Composable
actual fun rememberImagePicker(result: (String?) -> Unit): ImagePicker {
    return JsImagePicker(result)
}