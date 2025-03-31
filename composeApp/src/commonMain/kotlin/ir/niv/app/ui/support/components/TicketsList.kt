package ir.niv.app.ui.support.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.ContinuousFailedApi
import ir.niv.app.ui.core.ContinuousFetching
import ir.niv.app.ui.support.list.TicketUiModel
import ir.niv.app.ui.theme.theme.NivTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TicketsList(
    items: ImmutableList<TicketUiModel>,
    onItemClick: (Long) -> Unit,
    tickets: ContinuousDeferredData<ImmutableList<TicketUiModel>>,
    onRetryClick: () -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = items,
            key = { item ->
                item.id
            },
            itemContent = { ticket ->
                Column(modifier = Modifier.clickable(onClick = { onItemClick(ticket.id) })) {
                    Row(
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                            horizontal = 24.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = ticket.title)
                        Spacer(modifier = Modifier.weight(1f))
                        Badge(
                            modifier = Modifier,
                            containerColor = ticket.status.backgroundColor,
                            contentColor = ticket.status.contentColor,
                        ) {
                            Text(text = ticket.status.label)
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 24.dp),
                        color = NivTheme.colorScheme.outlineVariant
                    )
                }
            }
        )
        item {
            when (tickets) {
                is ContinuousFailedApi<*> -> {
                    ConnectLost(
                        onRetryClick = onRetryClick
                    )
                }

                is ContinuousFetching<*> -> {
                    TicketsShimmer(1)
                }

                else -> {}
            }
        }
    }
}