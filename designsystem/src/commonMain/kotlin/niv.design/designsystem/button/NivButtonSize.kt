package niv.design.designsystem.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import niv.design.designsystem.theme.NivTheme

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