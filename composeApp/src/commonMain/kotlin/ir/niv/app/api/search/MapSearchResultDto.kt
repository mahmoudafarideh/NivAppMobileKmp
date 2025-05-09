package ir.niv.app.api.search

import ir.niv.app.domain.search.MapSearchResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapSearchResultDto(
    @SerialName("filters")
    val filters: List<SearchFilterDto>,
    @SerialName("workout_types")
    val workouts: List<SearchWorkoutFilterDto> = listOf(),
    @SerialName("search")
    val gyms: List<MapSearchGymDto>,
    @SerialName("has_next")
    val hasNext: Boolean = false,
)

internal fun MapSearchResultDto.toDomain() = MapSearchResult(
    filters = filters.map { it.toDomain() },
    gyms = gyms.map { it.toDomain() },
)