package ir.niv.app.api.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchWorkoutFilterDto(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val id: Long,
    @SerialName("selected")
    val selected: Boolean,
)
