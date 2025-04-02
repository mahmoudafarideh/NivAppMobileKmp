package ir.niv.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import ir.niv.app.ui.utils.logInfo
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    runCatching {
        onWasmReady {
            ComposeViewport(document.body!!) {
                AppWithDI()
            }
        }
    }.onFailure {
        logInfo("SXO", it.message.orEmpty())
    }.onSuccess {
        logInfo("SXO", "onSuccess")
    }
}