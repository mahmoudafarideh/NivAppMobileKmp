package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ir.niv.app.domain.core.LatLng
import kotlinx.browser.window

@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: LatLng,
    zoom: Double
) {

}