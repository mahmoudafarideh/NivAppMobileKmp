package ir.niv.app.ui.home.graph

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.home.screen.HomeScreen
import ir.niv.app.ui.home.screen.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import ir.niv.app.ui.home.graph.routes.screen

fun NavGraphBuilder.homeGraph() {
    HomeRoute.screen(this) {
        val homeViewModel: HomeViewModel = koinViewModel()
        val state by homeViewModel.state.collectAsStateWithLifecycle()
        HomeScreen(
            user = state.user,
            grid = state.grid
        )
    }
}