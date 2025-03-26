package ir.niv.app.ui.splash.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.niv.app.ui.splash.graph.routes.screen
import ir.niv.app.ui.splash.screen.SplashScreen

fun NavGraphBuilder.splashNavGraph() {
    SplashRoute.screen(this) {
        SplashScreen()
    }
}