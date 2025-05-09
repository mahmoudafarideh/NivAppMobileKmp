package ir.niv.app.ui.search.model

import ir.niv.app.ui.core.LatLngUiModel

data class SearchMapUiModel(
    val center: LatLngUiModel = LatLngUiModel(35.73443, 51.356882),
    val showMyLocation: Boolean = false,
)
