package ir.niv.app.ui.search.model

import ir.niv.app.domain.search.MapSearchGym
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.core.toLatLng

data class GymMapUiModel(
    val id: String,
    val avatar: String,
    val open: Boolean,
    val latLng: LatLngUiModel
)

internal fun MapSearchGym.toGymMapUiModel() = GymMapUiModel(
    id = id,
    avatar = avatar.avatar,
    open = open,
    latLng = latLng.toLatLng()
)