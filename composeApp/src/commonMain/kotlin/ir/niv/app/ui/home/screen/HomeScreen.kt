package ir.niv.app.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.home.components.HomeAppBar
import ir.niv.app.ui.home.components.HomeSearchBar
import ir.niv.app.ui.home.models.UserUiModel
import ir.niv.app.ui.theme.shape.squircle.SquircleShape
import ir.niv.app.ui.theme.theme.NivTheme
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.notifications_24px
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: UserUiModel?,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeAppBar(user, Modifier)
        }
    ) {
        val itemShape = remember { SquircleShape() }
        val items = remember {
            listOf(
                Triple("باشگاه", Color(0xFFcefce1), Color(0xFF28af62)),
                Triple("تمرین", Color(0xFFfacfd6), Color(0xFFf33d5b)),
                Triple("غذا", Color(0xFFd2e3fa), Color(0xFF3080ed)),
                Triple("مربیان", Color(0xFFd7d7f9), Color(0xFF5556ce)),
                Triple("حرکات بدنسازی", Color(0xFFfbd2e1), Color(0xFFb3446c)),
                Triple("دوستان", Color(0xFFf2d9fb), Color(0xFFaa20dd))
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(it).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item(span = { GridItemSpan(3) }, key = "SearchBar") {
                HomeSearchBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp, end = 4.dp, top = 24.dp),
                    onSearchClick = {},
                    onMapClick = {}
                )
            }
            items(
                items = items,
                key = {
                    it
                }
            ) {
                Box(
                    modifier = Modifier.aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(itemShape)
                                .background(it.second),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.notifications_24px),
                                tint = it.third,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center).size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = it.first,
                            style = NivTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}