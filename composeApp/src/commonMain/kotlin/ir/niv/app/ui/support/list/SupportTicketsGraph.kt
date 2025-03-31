package ir.niv.app.ui.support.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import ir.niv.app.ui.core.DialogLayout
import ir.niv.app.ui.support.list.routes.screen
import ir.niv.app.ui.support.submit.SubmitTicketDialog
import ir.niv.app.ui.support.submit.SubmitTicketRoute
import ir.niv.app.ui.support.submit.SubmitTicketViewModel
import ir.niv.app.ui.support.submit.routes.dialog
import ir.niv.app.ui.support.submit.routes.navigator
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
            listState = listState,
            onNewButtonClick = {
                navigation.navigate(SubmitTicketRoute.navigator)
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
}