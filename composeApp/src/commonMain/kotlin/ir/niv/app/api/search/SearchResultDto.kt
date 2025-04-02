package ir.niv.app.api.search

import ir.niv.app.api.core.StateDto
import ir.niv.app.api.core.toDomain
import ir.niv.app.domain.search.SearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("current_city")
    val selectedCity: Long,
    @SerialName("filters")
    val filters: List<SearchFilterDto>,
    @SerialName("states")
    val states: List<StateDto>,
    @SerialName("workout_types")
    val workouts: List<SearchWorkoutFilterDto> = listOf(),
    @SerialName("search")
    val gyms: List<SearchGymDto>,
    @SerialName("has_next")
    val hasNext: Boolean = false,
)

internal fun SearchResultDto.toDomain() = SearchResult(
    selectedCity = selectedCity,
    filters = filters.map { it.toDomain() },
    states = states.map { it.toDomain() },
    gyms = gyms.map { it.toDomain() },
    hasNext = hasNext
)