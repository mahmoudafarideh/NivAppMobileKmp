package ir.niv.app.ui.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import m.a.compilot.navigation.controller.LocalNavController
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.vazirmatn_bold
import nivapp.composeapp.generated.resources.vazirmatn_medium
import nivapp.composeapp.generated.resources.vazirmatn_regular
import org.jetbrains.compose.resources.Font

@Composable
fun NivTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }
    val extendedColorScheme = when {
        darkTheme -> extendedDark
        else -> extendedLight
    }

    val typography = getTypography()
    CompositionLocalProvider(
        LocalExtendedColorScheme provides extendedColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = typography,
            content = content,
        )
    }
}

@Composable
fun NivThemePreview(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        CompositionLocalProvider(LocalNavController provides rememberNavController()) {
            NivTheme {
                content()
            }
        }
    }
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

val MaterialTheme.extendedColors: ExtendedColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColorScheme.current