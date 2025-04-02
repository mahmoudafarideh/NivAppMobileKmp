package ir.niv.app.ui.search.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.search.components.MapContainer
import ir.niv.app.ui.search.graph.routes.screen
import ir.niv.app.ui.search.screen.SearchScreen
import ir.niv.app.ui.search.screen.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.searchGraph() {
    SearchRoute.screen(this) {
        val viewModel: SearchViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        SearchScreen(
            query = state.query,
            queryLimit = state.showCharsLimit,
            onQueryChange = {
                viewModel.queryChanged(it)
            },
            gyms = state.gyms
        )
    }

    MapSearchRoute.screen(this) {
        MapContainer(
            modifier = Modifier.fillMaxSize()
        )
    }
}