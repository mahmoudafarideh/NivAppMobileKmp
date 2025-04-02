package ir.niv.app.ui.core

import ir.niv.app.domain.search.SearchGym

data class CityUiModel(
    val name: String,
    val state: String,
)

internal fun SearchGym.GymCity.toUiModel() = CityUiModel(name, state)