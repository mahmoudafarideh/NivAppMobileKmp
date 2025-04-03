package ir.niv.app.ui.search.components

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import ir.niv.app.R
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.annotation.Symbol
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions


@SuppressLint("MissingPermission")
@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: LatLngUiModel,
    zoom: Double,
    markers: ImmutableList<GymMapUiModel>,
    onCameraIdle: (LatLngUiModel) -> Unit,
) {
    val context = LocalContext.current
    val symbolManager = remember { mutableStateOf<Pair<Style, SymbolManager>?>(null) }
    val mapView = remember {
        MapLibre.getInstance(context)
        MapView(context).apply {
            getMapAsync { map ->
                map.uiSettings.isRotateGesturesEnabled = false
                map.uiSettings.isLogoEnabled = false
                map.uiSettings.isAttributionEnabled = false
                map.setStyle(styleUrl)
                map.getStyle {
                    symbolManager.value = it to SymbolManager(this, map, it).apply {
                        iconAllowOverlap = true
                        iconIgnorePlacement = true
                        addClickListener {
                            true
                        }
                    }
                }
            }
        }
    }

    val attachedMarkers = remember { mutableStateOf(listOf<Pair<String, Symbol>>()) }
    LaunchedEffect(symbolManager.value, markers) {
        val (style, symbolManager) = symbolManager.value ?: return@LaunchedEffect
        attachedMarkers.value.filter {
            it.first !in markers.map { gym -> gym.toString() }
        }.let { items ->
            items.forEach { (id, symbol) ->
                style.removeImage(id)
                symbolManager.delete(symbol)
            }
            attachedMarkers.value = attachedMarkers.value.filter { it !in items }
        }
        val markers = markers.filter {
            it.toString() !in attachedMarkers.value.map { symbol -> symbol.first }
        }.map {
            async {
                it to createMarkerBitmapWithBackground(
                    context, it.avatar, R.drawable.livepin
                )
            }
        }.awaitAll()
        markers.forEach { (gym, bitmap) ->
            val bitmap = bitmap ?: return@forEach
            style.addImage(gym.toString(), bitmap)
            val symbol = symbolManager.create(
                SymbolOptions()
                    .withLatLng(gym.latLng.toLatLng())
                    .withIconImage(gym.toString())
                    .withIconAnchor("bottom")
            )
            symbolManager.update(symbol)
            attachedMarkers.value += gym.toString() to symbol
        }
    }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (locationGranted(context)) {
            showUserLocation(mapView, context)
        }
    }
    LaunchedEffect(Unit) {
        if (!locationGranted(context)) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            showUserLocation(mapView, context)
        }
    }

    LaunchedEffect(Unit) {

        while (isActive) {
            delay(200)
            val camera = suspendCancellableCoroutine {
                mapView.getMapAsync { map ->
                    it.resumeWith(
                        runCatching {
                            map.locationComponent.lastKnownLocation?.toLatLng()
                        }.getOrNull().let { latLng ->
                            Result.success(latLng)
                        }
                    )
                }
            }
            if (camera != null) {
                mapView.getMapAsync { map ->
                    val position =
                        CameraPosition.Builder().target(camera.toLatLng()).zoom(zoom).build()
                    map.cameraPosition = position
                    this.cancel()
                }
            }
            delay(800)
        }
    }

    val center by rememberUpdatedState(center)
    LaunchedEffect(center) {
        mapView.getMapAsync { map ->
            val position = CameraPosition.Builder().target(center.toLatLng()).build()
            map.cameraPosition = position
        }
    }

    AndroidView(
        factory = { mapView }, modifier = modifier
    )

    MapLifecycleHandler(mapView, onCameraIdle)
}

@Composable
private fun MapLifecycleHandler(
    mapView: MapView,
    onCameraIdle: (LatLngUiModel) -> Unit
) {
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
        var mapLibreMap: MapLibreMap? = null
        val cameraListener = MapLibreMap.OnCameraIdleListener {
            mapLibreMap?.cameraPosition?.target?.let {
                onCameraIdle(it.toLatLng())
            }
        }

        mapView.getMapAsync {
            mapLibreMap = it
            it.addOnCameraIdleListener(cameraListener)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            mapView.onDestroy()
            mapLibreMap?.removeOnCameraIdleListener(cameraListener)
        }
    }
}


actual val mapEnabled: Boolean = true