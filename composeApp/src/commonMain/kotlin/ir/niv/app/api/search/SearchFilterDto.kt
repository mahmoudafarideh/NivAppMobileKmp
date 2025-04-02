package ir.niv.app.api.search

import ir.niv.app.domain.search.SearchFilter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchFilterDto(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val id: Long,
    @SerialName("selected")
    val selected: Boolean,
)

internal fun SearchFilterDto.toDomain() = SearchFilter(
    name = name,
    id = id,
    selected = selected,
)