package ir.niv.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.niv.app.ui.splash.graph.SplashRoute
import ir.niv.app.ui.splash.graph.splashNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    NivTheme {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavHost(
                navController = navController,
                startDestination = SplashRoute
            ) {
                splashNavGraph()
            }
        }
    }
}