package ir.niv.app.ui.support.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.Failed
import ir.niv.app.ui.core.FailedApi
import ir.niv.app.ui.core.Fetching
import ir.niv.app.ui.core.Retrieved
import ir.niv.app.ui.core.StatusUiModel
import ir.niv.app.ui.support.components.SelfMessageBubble
import ir.niv.app.ui.support.components.TicketChatBar
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import kotlinx.collections.immutable.persistentListOf
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsDetailScreen(
    state: DeferredData<TicketDetailsUiModel>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(
                        painter = painterResource(Res.drawable.arrow_small_right_24),
                        onClick = { }
                    )
                },
                actions = {
//                    Badge(
//                        modifier = Modifier,
//                        containerColor = ticket.status.backgroundColor,
//                        contentColor = ticket.status.contentColor,
//                    ) {
//                        Text(text = ticket.status.label)
//                    }
                }
            )
        }
    ) { padding ->
        Scaffold(
            modifier = Modifier.fillMaxSize().padding(padding),
            bottomBar = {
                TicketChatBar()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
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

@Composable
private fun AdminChatBubble(
    message: TicketMessageUiModel,
    sender: TicketMessageUiModel.Sender.Admin
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(
            end = 16.dp,
            bottom = 16.dp,
            start = 32.dp
        ),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = message.content,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            12.dp,
                            12.dp,
                            2.dp,
                            12.dp
                        )
                    )
                    .background(NivTheme.colorScheme.tertiaryContainer)
                    .padding(8.dp),
                color = NivTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = message.date,
                style = NivTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        UrlImage(
            url = sender.avatar,
            modifier = Modifier.size(38.dp).clip(
                CircleShape
            )
        )
    }
}


@Preview
@Composable
private fun TicketsDetailScreenPreview() {
    NivThemePreview {
        TicketsDetailScreen(
            state = Retrieved(
                TicketDetailsUiModel(
                    id = 1,
                    subject = "سلام",
                    status = StatusUiModel("جدید", StatusUiModel.State.Primary),
                    messages = persistentListOf(
                        TicketMessageUiModel(
                            content = "سلام ریدم تو این اپ آشغالتون. این چه گوهیه آخه؟؟؟؟!!!",
                            date = "۲۵ فروردین ۱۴۰۴",
                            sender = TicketMessageUiModel.Sender.Self
                        ),
                        TicketMessageUiModel(
                            content = "آقا درست صحبت کن یعنی چی؟",
                            date = "۲۵ فروردین ۱۴۰۴",
                            sender = TicketMessageUiModel.Sender.Admin("avatar", "fullName")
                        )
                    ),
                    closed = true
                )
            )
        )
    }
}