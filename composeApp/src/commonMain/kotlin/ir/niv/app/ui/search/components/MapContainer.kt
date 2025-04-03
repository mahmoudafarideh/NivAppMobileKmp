package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.domain.core.LatLng
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
expect fun MapContainer(
    modifier: Modifier = Modifier,
    styleUrl: String = "https://tiles.raah.ir/dynamic/new_style_preview.json",
    center: LatLngUiModel = LatLngUiModel(35.73443, 51.356882),
    zoom: Double = 14.0,
    markers: ImmutableList<GymMapUiModel> = persistentListOf(),
    onCameraIdle: (LatLngUiModel) -> Unit = {}
)

expect val mapEnabled: Boolean