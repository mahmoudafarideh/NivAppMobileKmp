package ir.niv.app.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.notifications_24px
import nivapp.composeapp.generated.resources.support_agent_24px
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    avatar: String?,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeAppBar(avatar, Modifier)
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppBar(
    avatar: String?,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            avatar?.let {
                UrlImage(
                    url = avatar,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .aspectRatio(1f, true)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(NivTheme.colorScheme.secondary)
                        .padding(1.dp)
                        .clip(CircleShape)
                )
            }

        },
        actions = {
            Icon(
                painter = painterResource(Res.drawable.notifications_24px),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
                    .padding(12.dp)
            )
            Icon(
                painter = painterResource(Res.drawable.support_agent_24px),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
                    .padding(12.dp)
            )
        }
    )
}