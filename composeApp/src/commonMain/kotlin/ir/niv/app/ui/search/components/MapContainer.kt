package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.niv.app.domain.core.LatLng

@Composable
expect fun MapContainer(
    modifier: Modifier = Modifier,
    styleUrl: String = "https://tiles.raah.ir/dynamic/new_style_preview.json",
    center: LatLng = LatLng(35.73443, 51.356882),
    zoom: Double = 12.0
)