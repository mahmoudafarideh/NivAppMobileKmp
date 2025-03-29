package ir.niv.app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.theme.theme.NivTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeSearchBar(
    onSearchClick: () -> Unit,
    onMapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProvideTextStyle(value = NivTheme.typography.bodyMedium) {
            SearchBarDefaults.InputField(
                query = "",
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                placeholder = {
                    Text(
                        text = "جستجوی باشگاه...",
                        style = NivTheme.typography.bodySmall
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = NivTheme.colorScheme.onSecondaryContainer
                    )
                },
                interactionSource = null,
                onQueryChange = {},
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .clickable {
                        onSearchClick()
                    }
                    .background(NivTheme.colorScheme.secondaryContainer),
                enabled = false
            )
        }
        IconButton(
            imageVector = Icons.Outlined.Map,
            modifier = Modifier.padding(start = 16.dp, end = 6.dp),
            onClick = onMapClick
        )
    }
}