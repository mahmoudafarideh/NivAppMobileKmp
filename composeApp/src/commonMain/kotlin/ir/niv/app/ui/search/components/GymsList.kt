package ir.niv.app.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.components.UrlImage
import ir.niv.app.ui.core.CityUiModel
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.ContinuousFailedApi
import ir.niv.app.ui.core.ContinuousFetching
import ir.niv.app.ui.core.GenderUiModel
import ir.niv.app.ui.core.InitialFetching
import ir.niv.app.ui.search.screen.SearchGymUiModel
import ir.niv.app.ui.theme.shape.squircle.SquircleShape
import ir.niv.app.ui.theme.theme.NivTheme
import ir.niv.app.ui.theme.theme.NivThemePreview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.fi_sr_marker
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun GymsList(
    items: ImmutableList<SearchGymUiModel>,
    onItemClick: (String) -> Unit,
    itemsState: ContinuousDeferredData<*>,
    onRetryClick: () -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    val shape = remember { SquircleShape() }
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = items,
            key = { item ->
                item.id
            },
            itemContent = { gym ->
                Column(modifier = Modifier.clickable(onClick = { onItemClick(gym.id) })) {
                    Row(
                        modifier = Modifier.padding(
                            vertical = 16.dp,
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
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                item {
                                    Icon(
                                        painter = painterResource(Res.drawable.fi_sr_marker),
                                        tint = NivTheme.colorScheme.onSurfaceVariant,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = "${gym.city.state} | ${gym.city.name}",
                                        color = NivTheme.colorScheme.onBackground,
                                        maxLines = 1,
                                        style = NivTheme.typography.bodyMedium
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Icon(
                                        painter = painterResource(gym.gender.icon),
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
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 80.dp),
                        color = NivTheme.colorScheme.outlineVariant
                    )
                }
            }
        )
        item {
            when (itemsState) {
                is ContinuousFailedApi<*> -> {
                    ConnectLost(
                        onRetryClick = onRetryClick,
                    )
                }

                is ContinuousFetching<*> -> {
                    SearchShimmer(1)
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
private fun GymsListPreview() {
    NivThemePreview {
        GymsList(
            items = persistentListOf(
                SearchGymUiModel(
                    id = "1",
                    name = "باشگاه خفن محمود و شرکا (مدیریت عالی)",
                    avatar = "",
                    gender = GenderUiModel.Both,
                    city = CityUiModel("تهرانسر", "تهران"),
                    genderLabel = "بانوان و آقایان"
                )
            ),
            onItemClick = {},
            onRetryClick = {},
            listState = rememberLazyListState(),
            itemsState = InitialFetching(1, 10)
        )
    }
}