package ir.niv.app.ui.theme.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.theme.NivTheme

enum class NivButtonSize(
    val height: Dp,
    val iconSize: Dp,
) {
    Large(56.dp, 48.dp),
    Medium(48.dp, 36.dp),
    Small(36.dp, 24.dp);

    @Composable
    fun textStyle() = when (this) {
        Large -> NivTheme.typography.labelLarge
        Medium -> NivTheme.typography.labelMedium
        Small -> NivTheme.typography.labelSmall
    }

}