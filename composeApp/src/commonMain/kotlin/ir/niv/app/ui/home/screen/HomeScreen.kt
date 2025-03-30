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
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.home.components.HomeAppBar
import ir.niv.app.ui.home.components.HomeSearchBar
import ir.niv.app.ui.home.models.HomeGridItemUiModel
import ir.niv.app.ui.home.models.UserUiModel
import ir.niv.app.ui.theme.shape.squircle.SquircleShape
import ir.niv.app.ui.theme.theme.NivTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: UserUiModel?,
    grid: List<HomeGridItemUiModel>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeAppBar(user, Modifier)
        }
    ) {
        val itemShape = remember { SquircleShape() }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(it).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item(span = { GridItemSpan(3) }, key = "SearchBar") {
                HomeSearchBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp, end = 4.dp, top = 24.dp, bottom = 16.dp),
                    onSearchClick = {},
                    onMapClick = {}
                )
            }
            items(
                items = grid,
                key = { grid -> grid.type.name }
            ) { grid ->
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
                                .background(grid.type.backgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(grid.type.icon),
                                tint = grid.type.iconTint,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.Center).size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(grid.type.label),
                            style = NivTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}