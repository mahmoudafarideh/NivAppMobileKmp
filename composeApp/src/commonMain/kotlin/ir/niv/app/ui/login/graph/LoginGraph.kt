package ir.niv.app.ui.login.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.niv.app.ui.login.graph.routes.screen
import ir.niv.app.ui.login.screen.LoginScreen

fun NavGraphBuilder.loginGraph() {
    LoginRoute.screen(this) {
        LoginScreen()
    }
}