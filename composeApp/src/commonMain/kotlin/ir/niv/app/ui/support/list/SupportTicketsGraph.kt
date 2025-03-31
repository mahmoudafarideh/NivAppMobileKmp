package ir.niv.app.ui.support.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.support.list.routes.screen
import ir.niv.app.ui.utils.InfiniteListScrollDown
import m.a.compilot.navigation.controller.LocalNavController
import m.a.compilot.navigation.controller.comPilotNavController
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.supportTicketsGraph() {
    SupportTicketsRoute.screen(this) {
        val viewModel: TicketsViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigation = LocalNavController.comPilotNavController
        val listState = rememberLazyListState()
        InfiniteListScrollDown(
            lazyListState = listState,
            onReachEnd = {
                viewModel.listReachedEnd()
            }
        )
        TicketScreen(
            tickets = state.tickets,
            onRetryClick = {
                viewModel.retryClicked()
            },
            onItemClick = {},
            onBackButtonClick = {
                navigation.safePopBackStack()
            },
            listState = listState
        )
    }
}