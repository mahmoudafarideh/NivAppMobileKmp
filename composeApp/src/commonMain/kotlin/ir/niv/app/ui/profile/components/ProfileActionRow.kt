package ir.niv.app.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.fi_sr_arrow_small_left
import nivapp.composeapp.generated.resources.fi_sr_user_pen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ProfileActionRow(
    resource: DrawableResource,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resource),
                tint = NivTheme.colorScheme.onBackground,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = label,
                color = NivTheme.colorScheme.onBackground,
                style = NivTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(Res.drawable.fi_sr_arrow_small_left),
                tint = NivTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(start = 56.dp),
            color = NivTheme.colorScheme.outlineVariant
        )
    }
}

@Preview
@Composable
private fun SearchMapScreenPreview() {
    NivThemePreview {
        ProfileActionRow(
            label = "ویرایش پروفایل",
            resource = Res.drawable.fi_sr_user_pen,
            onClick = {}
        )
    }
}