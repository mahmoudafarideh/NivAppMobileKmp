package ir.niv.app.ui.search.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import androidx.core.app.ActivityCompat
import ir.niv.app.domain.core.LatLng
import ir.niv.app.ui.core.LatLngUiModel
import org.maplibre.android.location.LocationComponentActivationOptions
import org.maplibre.android.location.LocationComponentOptions
import org.maplibre.android.location.engine.LocationEngineRequest
import org.maplibre.android.location.modes.CameraMode
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style

internal fun LatLng.toLatLng() = org.maplibre.android.geometry.LatLng(lat, lng)

internal fun locationGranted(context: Context): Boolean = ActivityCompat.checkSelfPermission(
    context, Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED

internal fun buildLocationComponentActivationOptions(
    context: Context, style: Style, options: LocationComponentOptions
): LocationComponentActivationOptions {
    return LocationComponentActivationOptions
        .builder(context, style)
        .locationComponentOptions(options)
        .useDefaultLocationEngine(true)
        .locationEngineRequest(
            LocationEngineRequest.Builder(750)
                .setFastestInterval(750)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .build()
        )
        .build()
}

internal fun LatLngUiModel.toLatLng() = org.maplibre.android.geometry.LatLng(lat, lng)
internal fun org.maplibre.android.geometry.LatLng.toLatLng() = LatLngUiModel(latitude, longitude)
internal fun Location.toLatLng() = LatLngUiModel(latitude, longitude)

@SuppressLint("MissingPermission")
internal fun showUserLocation(mapView: MapView, context: Context) {
    mapView.getMapAsync { mapLibre ->
        mapLibre.getStyle @androidx.annotation.RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) { style ->
            val locationComponent = mapLibre.locationComponent
            val locationComponentOptions =
                LocationComponentOptions.builder(context)
                    .pulseEnabled(true)
                    .build()

            val locationComponentActivationOptions =
                buildLocationComponentActivationOptions(context, style, locationComponentOptions)

            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.NONE
        }
    }
}

fun fakeTouch(mapView: View) {
    val downTime = SystemClock.uptimeMillis()
    val eventTime = SystemClock.uptimeMillis()
    val x = (mapView.width / 2).toFloat()
    val y = (mapView.height / 2).toFloat()

    val motionEvent = MotionEvent.obtain(
        downTime,
        eventTime,
        MotionEvent.ACTION_DOWN,
        x,
        y,
        0
    )
    mapView.dispatchTouchEvent(motionEvent)

    val motionEventUp = MotionEvent.obtain(
        downTime + 100,
        eventTime + 100,
        MotionEvent.ACTION_UP,
        x,
        y,
        0
    )
    mapView.dispatchTouchEvent(motionEventUp)
}