package ir.niv.app.ui.support.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.paper_plane_top_24
import org.jetbrains.compose.resources.painterResource


@Suppress("MemberExtensionConflict")
@Composable
internal fun TicketChatBar(
    onMessageSend: (String) -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }
    Row(
        modifier = modifier.padding(top = 16.dp).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(text = "پیام خود را بنویسید...")
            },
            shape = NivTheme.shapes.extraLarge,
            enabled = enable,
            supportingText = {}
        )
        AnimatedVisibility(
            visible = message.isNotBlank(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            IconButton(
                painter = painterResource(Res.drawable.paper_plane_top_24),
                onClick = {
                    onMessageSend(message)
                    message = ""
                },
                modifier = Modifier.padding(bottom = 12.dp).padding(start = 8.dp).rotate(180f),
                backgroundColor = NivTheme.colorScheme.tertiaryContainer,
                contentColor = NivTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}