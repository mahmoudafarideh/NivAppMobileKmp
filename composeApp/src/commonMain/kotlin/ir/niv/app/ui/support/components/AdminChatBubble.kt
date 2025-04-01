package ir.niv.app.ui.support.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.core.localized
import ir.niv.app.ui.support.details.TicketMessageUiModel
import ir.niv.app.ui.theme.theme.NivTheme

@Composable
internal fun AdminChatBubble(
    message: TicketMessageUiModel,
    sender: TicketMessageUiModel.Sender.Admin,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(
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
                text = message.date.localized(),
                style = NivTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        UrlImage(
            url = sender.avatar,
            modifier = Modifier.size(38.dp).clip(CircleShape)
        )
    }
}