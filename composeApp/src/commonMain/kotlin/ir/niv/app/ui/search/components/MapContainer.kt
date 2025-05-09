package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
expect fun MapContainer(
    modifier: Modifier = Modifier,
    styleUrl: String = "https://tiles.raah.ir/dynamic/new_style_preview.json",
    center: Flow<LatLngUiModel> = flowOf(),
    zoom: Double = 14.0,
    markers: Flow<ImmutableList<GymMapUiModel>> = flowOf(),
    onCameraIdle: (LatLngUiModel) -> Unit = {},
    onUserLocationChanged: (LatLngUiModel) -> Unit = {},
    onMarkerClicked: (GymMapUiModel) -> Unit = {},
)

expect val mapEnabled: Boolean