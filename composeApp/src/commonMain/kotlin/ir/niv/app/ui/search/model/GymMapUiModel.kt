package ir.niv.app.ui.search.model

import ir.niv.app.domain.search.MapSearchGym
import ir.niv.app.ui.core.GenderUiModel
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.core.toLatLng
import ir.niv.app.ui.core.toUiModel

data class GymMapUiModel(
    val id: String,
    val name: String,
    val avatar: String,
    val genderUiModel: GenderUiModel,
    val genderLabel: String,
    val open: Boolean,
    val latLng: LatLngUiModel,
    val selected: Boolean = false
)

internal fun MapSearchGym.toGymMapUiModel() = GymMapUiModel(
    id = id,
    name = name,
    avatar = avatar.avatar,
    open = open,
    latLng = latLng.toLatLng(),
    genderLabel = genderLabel,
    genderUiModel = gender.toUiModel()
)