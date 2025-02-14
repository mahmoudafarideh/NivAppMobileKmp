package niv.design.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import nivapp.designsystem.generated.resources.Res
import nivapp.designsystem.generated.resources.vazirmatn_bold
import nivapp.designsystem.generated.resources.vazirmatn_medium
import nivapp.designsystem.generated.resources.vazirmatn_regular
import org.jetbrains.compose.resources.Font

@Composable
fun NivTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit
) {
    val typography = getTypography()
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography,
        content = content,
    )
}

@Composable
private fun getTypography(): Typography = MaterialTheme.typography.let {
    it.copy(
        displayLarge = it.displayLarge.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        displayMedium = it.displayMedium.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        displaySmall = it.displaySmall.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        headlineLarge = it.headlineLarge.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_bold))
        ),
        headlineMedium = it.headlineMedium.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_bold))
        ),
        headlineSmall = it.headlineSmall.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_bold))
        ),
        titleLarge = it.titleLarge.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_medium))
        ),
        titleMedium = it.titleMedium.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_medium))
        ),
        titleSmall = it.titleSmall.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_medium))
        ),
        bodyLarge = it.bodyLarge.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        bodyMedium = it.bodyMedium.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        bodySmall = it.bodySmall.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        labelLarge = it.labelLarge.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        labelMedium = it.labelMedium.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
        labelSmall = it.labelSmall.copy(
            fontFamily = FontFamily(Font(Res.font.vazirmatn_regular))
        ),
    )
}

typealias NivTheme = MaterialTheme