package ir.niv.app.ui.search.components

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.search.model.GymMapUiModel
import ir.niv.app.ui.utils.logInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.annotation.Symbol
import org.maplibre.android.plugins.annotation.SymbolManager


@SuppressLint("MissingPermission")
@Composable
actual fun MapContainer(
    modifier: Modifier,
    styleUrl: String,
    center: Flow<LatLngUiModel>,
    zoom: Double,
    markers: Flow<ImmutableList<GymMapUiModel>>,
    onCameraIdle: (LatLngUiModel) -> Unit,
    onUserLocationChanged: (LatLngUiModel) -> Unit,
    onMarkerClicked: (GymMapUiModel) -> Unit
) {
    val context = LocalContext.current
    val symbolManager = remember { mutableStateOf<Pair<Style, SymbolManager>?>(null) }
    val attachedMarkers = remember { mutableStateOf(listOf<Pair<GymMapUiModel, Symbol>>()) }

    val mapView = remember {
        logInfo("SXOE", "CAME HERE")
        MapLibre.getInstance(context)
        MapView(context).apply {
            getMapAsync { map ->
                map.uiSettings.isRotateGesturesEnabled = false
                map.uiSettings.isLogoEnabled = false
                map.uiSettings.isAttributionEnabled = false
                map.setStyle(styleUrl)
                map.getStyle { style ->
                    symbolManager.value = style to SymbolManager(this, map, style).apply {
                        iconAllowOverlap = true
                        iconIgnorePlacement = true
                        addClickListener { symbol ->
                            attachedMarkers.value.firstOrNull { it ->
                                it.second == symbol
                            }?.let { gym ->
                                onMarkerClicked(gym.first)
                            } != null
                        }
                    }
                }
            }
        }
    }

    AttachMarkers(symbolManager, markers, context, attachedMarkers)

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

    GetUserLocation(mapView, onUserLocationChanged, zoom)

    LaunchedEffect(Unit) {
        center.collect {
            mapView.getMapAsync { map ->
                if (center == map.cameraPosition.target) return@getMapAsync
                val position = CameraPosition.Builder()
                    .target(it.toLatLng())
                    .zoom(zoom)
                    .build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(position)
                map.animateCamera(cameraUpdate)
            }
        }

    }

    AndroidView(
        factory = { mapView }, modifier = modifier
    )

    MapLifecycleHandler(mapView, onCameraIdle, symbolManager)
}

@Composable
private fun GetUserLocation(
    mapView: MapView,
    onUserLocationChanged: (LatLngUiModel) -> Unit,
    zoom: Double
) {
    val isMapMovedOnce = remember { mutableStateOf(false) }
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
                onUserLocationChanged(camera)
                if (!isMapMovedOnce.value) {
                    mapView.getMapAsync { map ->
                        val position =
                            CameraPosition.Builder().target(camera.toLatLng()).zoom(zoom).build()
                        map.cameraPosition = position
                        isMapMovedOnce.value = true
                    }
                }
            }
            delay(800)
        }
    }
}


@Composable
private fun MapLifecycleHandler(
    mapView: MapView,
    onCameraIdle: (LatLngUiModel) -> Unit,
    symbolManager: MutableState<Pair<Style, SymbolManager>?>
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
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy().also {
                symbolManager.value?.second?.onDestroy()
            }
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
            symbolManager.value?.second?.onDestroy()
        }
    }
}

actual val mapEnabled: Boolean = true