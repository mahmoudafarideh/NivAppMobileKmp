package ir.niv.app.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import ir.niv.app.domain.core.LatLng
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.maps.MapView

private fun LatLng.toLatLng() = org.maplibre.android.geometry.LatLng(lat, lng)

@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: LatLng,
    zoom: Double
) {
    val context = LocalContext.current
    val mapView = remember {
        MapLibre.getInstance(context)
        MapView(context).apply {
            getMapAsync { map ->
                map.uiSettings.isRotateGesturesEnabled = false
                map.setStyle(styleUrl)
            }
        }
    }

    val center by rememberUpdatedState(center)
    LaunchedEffect(center) {
        mapView.getMapAsync { map ->
            map.setStyle(styleUrl)
            val position = CameraPosition.Builder()
                .target(center.toLatLng())
                .zoom(zoom)
                .build()
            map.cameraPosition = position
        }
    }
    AndroidView(
        factory = { mapView },
        modifier = modifier
    )

    // Handle lifecycle
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }
}