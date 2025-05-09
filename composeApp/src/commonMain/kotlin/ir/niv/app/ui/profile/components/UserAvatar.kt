package ir.niv.app.ui.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
internal fun UserAvatar(
    avatar: String,
    modifier: Modifier = Modifier
) {
    UrlImage(
        url = avatar,
        modifier = modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(NivTheme.colorScheme.outlineVariant)
            .padding(1.dp)
            .clip(CircleShape)
            .background(NivTheme.colorScheme.background)
            .padding(7.dp)
            .clip(CircleShape)
    )
}

@Preview
@Composable
private fun SearchMapScreenPreview() {
    NivThemePreview {
        UserAvatar(
            avatar = ""
        )
    }
}