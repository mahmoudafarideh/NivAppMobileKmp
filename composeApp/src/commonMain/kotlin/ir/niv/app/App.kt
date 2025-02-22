package ir.niv.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.niv.app.di.koinModules
import ir.niv.app.ui.home.graph.homeGraph
import ir.niv.app.ui.login.graph.loginGraph
import ir.niv.app.ui.splash.graph.SplashRoute
import ir.niv.app.ui.splash.graph.splashNavGraph
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.utils.LocalNavController
import ir.niv.app.ui.utils.LocalSnackBarHostState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun AppWithDI() {
    KoinApplication(
        application = { modules(koinModules) }
    ) {
        App()
    }

}

@Composable
@Preview
fun App() {
    NivTheme {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl,
            LocalNavController provides navController,
            LocalSnackBarHostState provides snackbarHostState
        ) {
            Box {
                NavHost(
                    navController = navController,
                    startDestination = SplashRoute
                ) {
                    splashNavGraph()
                    loginGraph()
                    homeGraph()
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}