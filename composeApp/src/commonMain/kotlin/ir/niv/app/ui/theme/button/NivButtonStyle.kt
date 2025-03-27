package ir.niv.app.ui.theme.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.extendedColors

enum class NivButtonStyle {
    Primary,
    Danger,
    Success,
    Neutral;

    @Composable
    @ReadOnlyComposable
    fun getBackgroundColor(buttonState: NivButtonState) = when (this) {
        Primary -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.primaryContainer
            else -> NivTheme.colorScheme.primary
        }


        Danger -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.errorContainer
            else -> NivTheme.colorScheme.error
        }

        Success -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.extendedColors.success.colorContainer
            else -> NivTheme.extendedColors.success.color
        }

        Neutral -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.surfaceContainer
            else -> NivTheme.colorScheme.surface
        }
    }


    @Composable
    @ReadOnlyComposable
    fun getContentColor(buttonState: NivButtonState): Color = when (this) {
        Primary -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.onPrimaryContainer
            else -> NivTheme.colorScheme.onPrimary
        }


        Danger -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.onErrorContainer
            else -> NivTheme.colorScheme.onError
        }

        Neutral -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.colorScheme.onSurface
            else -> NivTheme.colorScheme.onSurface
        }

        Success -> when (buttonState) {
            NivButtonState.Disable -> NivTheme.extendedColors.success.colorContainer
            else -> NivTheme.extendedColors.success.onColorContainer
        }
    }

}