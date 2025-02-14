package ir.niv.app.ui.splash.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.niv.app.ui.splash.screen.SplashScreen

fun NavGraphBuilder.splashNavGraph() {
    composable<SplashRoute> {
        SplashScreen()
    }
}