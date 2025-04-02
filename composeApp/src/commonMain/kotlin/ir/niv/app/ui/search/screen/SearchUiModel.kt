package ir.niv.app.ui.search.screen

import ir.niv.app.domain.search.SearchGym
import ir.niv.app.ui.core.CityUiModel
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.GenderUiModel
import ir.niv.app.ui.core.ReadyToInitialFetch
import ir.niv.app.ui.core.toUiModel
import kotlinx.collections.immutable.ImmutableList

data class SearchUiModel(
    val query: String = "",
    val gyms: ContinuousDeferredData<ImmutableList<SearchGymUiModel>> = ReadyToInitialFetch(1, 10),
) {
    val showCharsLimit: Boolean = query.trim().length < 2 && gyms is ReadyToInitialFetch
}

data class SearchGymUiModel(
    val id: String,
    val name: String,
    val avatar: String,
    val gender: GenderUiModel,
    val genderLabel: String,
    val city: CityUiModel
)

internal fun SearchGym.toUiModel() = SearchGymUiModel(
    id = id,
    name = name,
    avatar = avatar.avatar,
    gender = gender.toUiModel(),
    city = city.toUiModel(),
    genderLabel = genderLabel
)