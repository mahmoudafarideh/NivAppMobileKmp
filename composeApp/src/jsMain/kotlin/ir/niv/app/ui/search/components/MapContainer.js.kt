package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: Flow<LatLngUiModel>,
    zoom: Double,
    markers: Flow<ImmutableList<GymMapUiModel>>,
    onCameraIdle: (LatLngUiModel) -> Unit,
    onUserLocationChanged: (LatLngUiModel) -> Unit,
    onMarkerClicked: (GymMapUiModel) -> Unit,
) {
}

actual val mapEnabled: Boolean = false