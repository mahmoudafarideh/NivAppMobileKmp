package ir.niv.app.ui.profile.graph

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.profile.crop.SquareCropper
import ir.niv.app.ui.profile.graph.routes.navigator
import ir.niv.app.ui.profile.graph.routes.screen
import ir.niv.app.ui.profile.screen.ProfileScreen
import ir.niv.app.ui.profile.screen.ProfileViewModel
import m.a.compilot.navigation.controller.LocalNavController
import m.a.compilot.navigation.controller.comPilotNavController
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.profileGraph() {
    ProfileRoute.screen(this) {
        val viewModel: ProfileViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navController = LocalNavController.comPilotNavController
        ProfileScreen(
            avatar = state.avatar,
            userName = state.name + " " + state.lastname,
            onPhotoSelect = {
                viewModel.photoSelected(it)
                navController.navigate(PhotoCropRoute.navigator)
            }
        )
    }

    PhotoCropRoute.screen(this) {
//        SquareCropper()
    }
}