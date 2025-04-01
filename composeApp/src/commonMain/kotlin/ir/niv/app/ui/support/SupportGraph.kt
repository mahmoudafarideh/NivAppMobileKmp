package ir.niv.app.ui.support

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.core.DialogLayout
import ir.niv.app.ui.core.onRetrieve
import ir.niv.app.ui.support.details.TicketDetailsRoute
import ir.niv.app.ui.support.details.TicketDetailsViewModel
import ir.niv.app.ui.support.details.TicketsDetailScreen
import ir.niv.app.ui.support.details.routes.navigator
import ir.niv.app.ui.support.details.routes.screen
import ir.niv.app.ui.support.list.SupportTicketsRoute
import ir.niv.app.ui.support.list.TicketScreen
import ir.niv.app.ui.support.list.TicketsViewModel
import ir.niv.app.ui.support.list.routes.screen
import ir.niv.app.ui.support.submit.SubmitTicketDialog
import ir.niv.app.ui.support.submit.SubmitTicketRoute
import ir.niv.app.ui.support.submit.SubmitTicketViewModel
import ir.niv.app.ui.support.submit.routes.dialog
import ir.niv.app.ui.support.submit.routes.navigator
import ir.niv.app.ui.utils.InfiniteListScrollDown
import m.a.compilot.navigation.controller.LocalNavController
import m.a.compilot.navigation.controller.NavigationResultHandler
import m.a.compilot.navigation.controller.comPilotNavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.supportTicketsGraph() {
    SupportTicketsRoute.screen(this) {
        val viewModel: TicketsViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigation = LocalNavController.comPilotNavController
        val listState = rememberLazyListState()
        it.navBackStackEntry.NavigationResultHandler {
            this.handleNavigationResult("create_ticket") {
                viewModel.refreshRequested()
                navigation.safeNavigate().navigate(
                    TicketDetailsRoute(this.getString("id")!!.toLong()).navigator
                )
            }
        }
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
            onItemClick = { id ->
                navigation.safeNavigate().navigate(
                    TicketDetailsRoute(id).navigator
                )
            },
            onBackButtonClick = {
                navigation.safePopBackStack()
            },
            listState = listState,
            onNewButtonClick = {
                navigation.safeNavigate().navigate(SubmitTicketRoute.navigator)
            },
            onRefresh = {
                viewModel.refreshRequested()
            }
        )
    }

    SubmitTicketRoute.dialog(
        this,
        dialogProperties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        val viewModel: SubmitTicketViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigation = LocalNavController.comPilotNavController
        val messageError by viewModel.apiErrors("message").collectAsStateWithLifecycle()
        val subjectError by viewModel.apiErrors("subject").collectAsStateWithLifecycle()
        LaunchedEffect(state.state) {
            state.state.onRetrieve {
                navigation.setResult("create_ticket") {
                    setString("id", "$it")
                }.safePopBackStack()
            }
        }
        DialogLayout(
            content = {
                SubmitTicketDialog(
                    state = state,
                    onCategoryClick = {
                        viewModel.categorySelected(it)
                    },
                    onSubjectChange = {
                        viewModel.subjectChanged(it)
                    },
                    onMessageChange = {
                        viewModel.messageChanged(it)
                    },
                    onSubmitClick = {
                        viewModel.submitButtonClicked()
                    },
                    messageError = messageError,
                    subjectError = subjectError
                )
            },
            onDismissClick = {
                navigation.safePopBackStack()
            }
        )
    }

    TicketDetailsRoute.screen(this) { it ->
        val viewModel: TicketDetailsViewModel = koinViewModel { parametersOf(it.argument.id) }
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigation = LocalNavController.comPilotNavController
        TicketsDetailScreen(
            state = state,
            onBackPressed = {
                navigation.safePopBackStack()
            },
            onMessageSend = {
                viewModel.sendMessageClicked(it)
            }
        )
    }
}