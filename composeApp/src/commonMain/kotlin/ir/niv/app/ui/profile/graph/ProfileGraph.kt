package ir.niv.app.ui.profile.graph

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.login.graph.routes.screen
import ir.niv.app.ui.profile.graph.routes.screen
import ir.niv.app.ui.profile.screen.ProfileScreen
import ir.niv.app.ui.profile.screen.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.profileGraph() {
    ProfileRoute.screen(this) {
        val viewModel: ProfileViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        ProfileScreen(
            avatar = state.avatar,
            userName = state.name + " " + state.lastname
        )
    }
}