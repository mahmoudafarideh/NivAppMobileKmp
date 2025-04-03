package ir.niv.app.ui.search.model

import ir.niv.app.ui.core.LatLngUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchMapUiModel(
    val center: LatLngUiModel = LatLngUiModel(35.73443, 51.356882),
    val gyms: ImmutableList<GymMapUiModel> = persistentListOf(),
    val showMyLocation: Boolean = false,
)
