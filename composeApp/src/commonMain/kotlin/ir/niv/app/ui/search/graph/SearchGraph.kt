package ir.niv.app.ui.search.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.search.components.MapContainer
import ir.niv.app.ui.search.graph.routes.screen
import ir.niv.app.ui.search.screen.SearchMapViewModel
import ir.niv.app.ui.search.screen.SearchScreen
import ir.niv.app.ui.search.screen.SearchViewModel
import m.a.compilot.navigation.controller.LocalNavController
import m.a.compilot.navigation.controller.comPilotNavController
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.searchGraph() {
    SearchRoute.screen(this) {
        val navigation = LocalNavController.comPilotNavController
        val viewModel: SearchViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        SearchScreen(
            query = state.query,
            queryLimit = state.showCharsLimit,
            onQueryChange = {
                viewModel.queryChanged(it)
            },
            gyms = state.gyms,
            onBackButtonClick = {
                navigation.safePopBackStack()
            }
        )
    }

    MapSearchRoute.screen(this) {
        val viewModel: SearchMapViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        MapContainer(
            modifier = Modifier.fillMaxSize(),
            markers = state.gyms,
            onCameraIdle = {
                viewModel.cameraIdled(it)
            },
            center = state.center
        )
    }
}