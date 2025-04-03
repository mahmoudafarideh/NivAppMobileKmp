package ir.niv.app.ui.core

import ir.niv.app.domain.core.LatLng

data class LatLngUiModel(
    val lat: Double,
    val lng: Double
)

internal fun LatLngUiModel.toLatLng() = LatLng(lat, lng)
internal fun LatLng.toLatLng() = LatLngUiModel(lat, lng)