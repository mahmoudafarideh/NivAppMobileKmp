package ir.niv.app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        ComposeViewport(document.body!!) {
            val fontFamilyResolver = createFontFamilyResolver()
            CompositionLocalProvider(
                LocalDensity provides Density(1.0f),
                LocalFontFamilyResolver provides fontFamilyResolver
            ) {
                AppWithDI()
            }
        }
    })
}