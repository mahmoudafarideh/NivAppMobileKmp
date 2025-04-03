package ir.niv.app.ui.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ir.niv.app.ui.components.ConnectLost
import ir.niv.app.ui.components.EmptyListMessage
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.InitialFailedApi
import ir.niv.app.ui.core.InitialFetching
import ir.niv.app.ui.core.ReadyToInitialFetch
import ir.niv.app.ui.search.components.GymsList
import ir.niv.app.ui.search.components.SearchAppBar
import ir.niv.app.ui.search.components.SearchShimmer
import ir.niv.app.ui.theme.theme.NivThemePreview
import kotlinx.collections.immutable.ImmutableList
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.comments_24
import nivapp.composeapp.generated.resources.fi_sr_category
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    gyms: ContinuousDeferredData<ImmutableList<SearchGymUiModel>>,
    query: String,
    queryLimit: Boolean,
    onQueryChange: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchAppBar(
                query = query,
                onQueryChange = onQueryChange,
                onBackButtonClick = onBackButtonClick
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            AnimatedVisibility(
                visible = queryLimit,
                modifier = Modifier.align(Alignment.Center),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                EmptyListMessage(
                    icon = painterResource(Res.drawable.fi_sr_category),
                    message = "حداقل ۲ حرف برای جستجو وارد کنید...",
                    buttonLabel = null,
                    modifier = Modifier.align(Alignment.Center),
                    paddingValues = PaddingValues(
                        top = it.calculateBottomPadding(),
                        bottom = it.calculateTopPadding(),
                    )
                )
            }
            when (gyms) {
                is InitialFailedApi -> {
                    ConnectLost(
                        modifier = Modifier.align(Alignment.Center),
                        paddingValues = PaddingValues(
                            bottom = it.calculateTopPadding(),
                            top = it.calculateBottomPadding()
                        ),
                        onRetryClick = {},
                    )
                }

                is InitialFetching -> {
                    SearchShimmer()
                }

                is ReadyToInitialFetch -> {}

                else -> {
                    gyms.data?.let { items ->
                        if (items.isEmpty()) {
                            EmptyListMessage(
                                icon = painterResource(Res.drawable.comments_24),
                                message = "هیچ باشگاهی یافت نشد!",
                                buttonLabel = null,
                                modifier = Modifier.align(Alignment.Center),
                                paddingValues = PaddingValues(
                                    bottom = it.calculateTopPadding(),
                                    top = it.calculateBottomPadding()
                                )
                            )
                        } else {
                            GymsList(
                                items = items,
                                onItemClick = {},
                                itemsState = gyms,
                                onRetryClick = {},
                                listState = rememberLazyListState(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    NivThemePreview {
        SearchScreen(
            query = "",
            queryLimit = false,
            onQueryChange = {},
            gyms = InitialFetching(1, 5),
            onBackButtonClick = {}
        )
    }
}