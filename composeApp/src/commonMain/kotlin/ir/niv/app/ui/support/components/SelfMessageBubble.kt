package ir.niv.app.ui.support.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.core.localized
import ir.niv.app.ui.support.details.TicketMessageUiModel
import ir.niv.app.ui.theme.theme.NivTheme

@Composable
internal fun SelfMessageBubble(
    message: TicketMessageUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            end = 32.dp,
            bottom = 16.dp,
            start = 16.dp
        )
    ) {
        Text(
            text = message.content,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        12.dp,
                        12.dp,
                        12.dp,
                        2.dp
                    )
                )
                .background(NivTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            color = NivTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = message.date.localized(),
            style = NivTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}