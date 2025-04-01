package ir.niv.app.ui.support.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.Failed
import ir.niv.app.ui.core.FailedApi
import ir.niv.app.ui.core.Fetching
import ir.niv.app.ui.core.Retrieved
import ir.niv.app.ui.support.components.AdminChatBubble
import ir.niv.app.ui.support.components.SelfMessageBubble
import ir.niv.app.ui.support.components.TicketChatBar
import ir.niv.app.ui.support.components.TicketChatBubblesShimmer
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsDetailScreen(
    state: DeferredData<TicketDetailsUiModel>,
    onBackPressed: () -> Unit,
    onMessageSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    val title = state.data?.subject.orEmpty()
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(
                        painter = painterResource(Res.drawable.arrow_small_right_24),
                        onClick = onBackPressed
                    )
                },
                actions = {
                    val status = state.data?.status
                    status?.let {
                        Badge(
                            modifier = Modifier.padding(end = 16.dp),
                            containerColor = it.backgroundColor,
                            contentColor = it.contentColor,
                        ) {
                            Text(text = it.label)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Scaffold(
            modifier = Modifier.fillMaxSize().padding(padding),
            bottomBar = {
                TicketChatBar(
                    onMessageSend = onMessageSend,
                    enable = state.data?.closed == false
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
                    .background(NivTheme.colorScheme.surfaceContainerLow)
            ) {
                when (state) {
                    Failed, is FailedApi -> {
                        ConnectLost(
                            onRetryClick = {},
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    Fetching -> {
                        TicketChatBubblesShimmer(modifier = Modifier.align(Alignment.BottomCenter))
                    }

                    is Retrieved<*> -> {
                        state.data?.messages?.let { messages ->
                            LazyColumn(
                                reverseLayout = true,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = messages,
                                    itemContent = { message ->
                                        when (message.sender) {
                                            is TicketMessageUiModel.Sender.Admin -> {
                                                AdminChatBubble(message, message.sender)
                                            }

                                            TicketMessageUiModel.Sender.Self -> {
                                                SelfMessageBubble(message)
                                            }
                                        }

                                    }
                                )
                            }
                        }

                    }

                    else -> {}
                }
            }
        }
    }
}


@Preview
@Composable
private fun TicketsDetailScreenPreview() {
    NivThemePreview {
        TicketsDetailScreen(
            state = Fetching,
            onBackPressed = {},
            onMessageSend = {},
        )
    }
}