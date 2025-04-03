package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.domain.core.LatLng
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList

@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: LatLngUiModel,
    zoom: Double,
    markers: ImmutableList<GymMapUiModel>,
    onCameraIdle: (LatLngUiModel) -> Unit,
) {
}

actual val mapEnabled: Boolean = false