package ir.niv.app.ui.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.core.GenderUiModel
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import ir.niv.app.ui.theme.button.NivButton
import ir.niv.app.ui.theme.shape.squircle.SquircleShape
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import ir.niv.app.ui.theme.theme.extendedColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GymInfoScreen(
    gym: GymMapUiModel,
    modifier: Modifier = Modifier
) {
    val shape = remember { SquircleShape() }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 24.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UrlImage(
                url = gym.avatar,
                modifier = Modifier.size(64.dp).clip(shape),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = gym.name,
                    color = NivTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(gym.genderUiModel.icon),
                        tint = NivTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = gym.genderLabel,
                        color = NivTheme.colorScheme.onBackground,
                        maxLines = 1,
                        style = NivTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = when {
                    gym.open -> "در حال حاضر باز است!"
                    else -> "در حال حاضر بسته است!"
                },
            color = when {
                !gym.open -> NivTheme.colorScheme.error
                else -> NivTheme.extendedColors.success.color
            },
        )
        Spacer(modifier = Modifier.size(24.dp))
        NivButton(
            label = "صفحه باشگاه",
            onClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview
@Composable
private fun SearchMapScreenPreview() {
    NivThemePreview {
        GymInfoScreen(
            gym = GymMapUiModel(
                id = "id",
                avatar = "",
                open = true,
                latLng = LatLngUiModel(35.73443, 51.356882),
                selected = false,
                name = "باشگاه خیلی خوشگل زیبایی که در جهان نیست",
                genderLabel = "بانوان و آقابان",
                genderUiModel = GenderUiModel.Both
            )
        )
    }
}