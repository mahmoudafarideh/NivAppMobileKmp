package ir.niv.app.ui.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Doorbell
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    avatar: String?,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {

                },
                actions = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Message,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = modifier
                            .clip(CircleShape)
                            .clickable { }
                            .padding(12.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Doorbell,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                        modifier = modifier
                            .clip(CircleShape)
                            .clickable { }
                            .padding(12.dp)
                    )
                }
            )
        }
    ) {

    }
}