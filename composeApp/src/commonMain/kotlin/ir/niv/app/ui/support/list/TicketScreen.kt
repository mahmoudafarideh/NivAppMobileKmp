package ir.niv.app.ui.support.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.components.EmptyListMessage
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.core.ApiError
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.ContinuousFailedApi
import ir.niv.app.ui.core.InitialFailedApi
import ir.niv.app.ui.core.InitialFetching
import ir.niv.app.ui.core.ReadyToInitialFetch
import ir.niv.app.ui.core.StatusUiModel
import ir.niv.app.ui.support.components.TicketsList
import ir.niv.app.ui.support.components.TicketsShimmer
import ir.niv.app.ui.theme.theme.NivThemePreview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import nivapp.composeapp.generated.resources.comments_24
import nivapp.composeapp.generated.resources.plus_small_24
import nivapp.composeapp.generated.resources.tickets_list_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    tickets: ContinuousDeferredData<ImmutableList<TicketUiModel>>,
    listState: LazyListState,
    onRetryClick: () -> Unit,
    onRefresh: () -> Unit,
    onNewButtonClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(Res.string.tickets_list_title))
                },
                navigationIcon = {
                    IconButton(
                        painter = painterResource(Res.drawable.arrow_small_right_24),
                        onClick = onBackButtonClick
                    )
                },
                actions = {
                    IconButton(
                        painter = painterResource(Res.drawable.plus_small_24),
                        onClick = onNewButtonClick
                    )
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            when (tickets) {
                is InitialFailedApi -> {
                    ConnectLost(
                        onRetryClick = onRetryClick,
                        modifier = Modifier.align(Alignment.Center),
                        paddingValues = PaddingValues(
                            bottom = it.calculateTopPadding(),
                            top = it.calculateBottomPadding()
                        )
                    )
                }

                is InitialFetching -> {
                    TicketsShimmer()
                }

                is ReadyToInitialFetch -> {}

                else -> {
                    tickets.data?.let { items ->
                        if (items.isEmpty()) {
                            EmptyListMessage(
                                icon = painterResource(Res.drawable.comments_24),
                                message = "هیچ پیامی ثبت نکرده‌اید! برای ارسال پیام جدید به پشتیبانی روی دکمه زیر کلیک کنید.",
                                buttonLabel = "ارسال پیام جدید",
                                onButtonClick = {},
                                modifier = Modifier.align(Alignment.Center),
                                paddingValues = PaddingValues(
                                    bottom = it.calculateTopPadding(),
                                    top = it.calculateBottomPadding()
                                )
                            )
                        } else {
                            PullToRefreshBox(
                                isRefreshing = false,
                                onRefresh = onRefresh,
                                content = {
                                    TicketsList(
                                        items = items,
                                        onItemClick = onItemClick,
                                        tickets = tickets,
                                        onRetryClick = onRetryClick,
                                        listState = listState
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun TicketScreenPreview() {
    NivThemePreview {
        TicketScreen(
            tickets = ContinuousFailedApi(
                persistentListOf(
                    TicketUiModel(
                        id = 1,
                        title = "سلام",
                        status = StatusUiModel(
                            "جدید", StatusUiModel.State.Warning
                        )
                    )
                ),
                ApiError(
                    null, null, ApiError.ErrorStatus.INTERNAL_SERVER_ERROR
                ),
                1,
                20
            ),
            onRetryClick = {},
            onItemClick = {},
            onNewButtonClick = {},
            onBackButtonClick = {},
            listState = rememberLazyListState(),
            onRefresh = {}
        )
    }
}