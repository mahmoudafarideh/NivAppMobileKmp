package ir.niv.app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.home.models.UserUiModel
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.home_hello_label
import nivapp.composeapp.generated.resources.notifications_24px
import nivapp.composeapp.generated.resources.support_agent_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeAppBar(
    user: UserUiModel?,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            user?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    UrlImage(
                        url = it.avatar,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .aspectRatio(1f, true)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(NivTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            text = stringResource(Res.string.home_hello_label),
                            style = NivTheme.typography.labelSmall
                        )
                        Text(
                            text = it.name,
                            style = NivTheme.typography.titleMedium
                        )
                    }
                }
            }

        },
        actions = {
            IconButton(
                painter = painterResource(Res.drawable.notifications_24px),
                modifier = modifier.padding(vertical = 4.dp),
                onClick = {}
            )
            IconButton(
                painter = painterResource(Res.drawable.support_agent_24px),
                modifier = modifier.padding(4.dp),
                onClick = {}
            )
        }
    )
}