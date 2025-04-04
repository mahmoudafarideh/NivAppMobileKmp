package ir.niv.app.ui.search.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.core.DialogLayout
import ir.niv.app.ui.search.graph.routes.dialog
import ir.niv.app.ui.search.graph.routes.navigator
import ir.niv.app.ui.search.graph.routes.screen
import ir.niv.app.ui.search.screen.GymInfoScreen
import ir.niv.app.ui.search.screen.SearchMapScreen
import ir.niv.app.ui.search.screen.SearchMapViewModel
import ir.niv.app.ui.search.screen.SearchScreen
import ir.niv.app.ui.search.screen.SearchViewModel
import ir.niv.app.ui.utils.logInfo
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
        val navigation = LocalNavController.comPilotNavController
        val viewModel: SearchMapViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(Unit) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.screenResumed()
            }
        }
        SearchMapScreen(
            onBackClick = {
                navigation.safePopBackStack()
            },
            state = state,
            onCameraIdle = {
                viewModel.cameraIdled(it)
            },
            onUserLocationChanged = {
                viewModel.userLocationChanged(it)
            },
            onUserLocationClick = {
                viewModel.userLocationClicked()
            },
            center = viewModel.center,
            onMarkerClicked = {
                viewModel.markerClicked(it)
                navigation.navigate(MapGymDetails(it).navigator)
            },
            gyms = viewModel.gyms
        )

    }
    MapGymDetails.dialog(
        this,
        dialogProperties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        val navigation = LocalNavController.comPilotNavController
        RemoveDim()
        DialogLayout(
            onDismissClick = {
                navigation.safePopBackStack()
            },
            content = {
                GymInfoScreen(
                    gym = it.argument.gym
                )
            }
        )
    }
}

@Composable
expect fun RemoveDim()