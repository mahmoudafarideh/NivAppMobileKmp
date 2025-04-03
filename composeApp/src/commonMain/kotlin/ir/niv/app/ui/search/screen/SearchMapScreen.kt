package ir.niv.app.ui.search.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.niv.app.ui.components.IconButton
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.components.MapContainer
import ir.niv.app.ui.search.model.GymMapUiModel
import ir.niv.app.ui.search.model.SearchMapUiModel
import ir.niv.app.ui.theme.theme.NivTheme
import kotlinx.coroutines.flow.Flow
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.arrow_small_right_24
import nivapp.composeapp.generated.resources.fi_br_location_crosshairs
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMapScreen(
    state: SearchMapUiModel,
    onBackClick: () -> Unit,
    onUserLocationClick: () -> Unit,
    onCameraIdle: (LatLngUiModel) -> Unit,
    onUserLocationChanged: (LatLngUiModel) -> Unit,
    center: Flow<LatLngUiModel>,
    onMarkerClicked: (GymMapUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        painter = painterResource(Res.drawable.arrow_small_right_24),
                        onClick = onBackClick,
                        backgroundColor = NivTheme.colorScheme.surface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = NivTheme.colorScheme.surface
                ),
            )
        }
    ) {
        Box {
            MapContainer(
                modifier = Modifier.fillMaxSize(),
                markers = state.gyms,
                onCameraIdle = onCameraIdle,
                center = center,
                onUserLocationChanged = onUserLocationChanged,
                onMarkerClicked = onMarkerClicked
            )
            Box(modifier = Modifier.padding(it).fillMaxSize()) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 16.dp, start = 4.dp)
                ) {
                    AnimatedVisibility(state.showMyLocation) {
                        IconButton(
                            painter = painterResource(Res.drawable.fi_br_location_crosshairs),
                            onClick = onUserLocationClick,
                            backgroundColor = NivTheme.colorScheme.surface
                        )
                    }
                }
            }
        }
    }
}