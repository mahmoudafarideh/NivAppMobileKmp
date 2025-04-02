package ir.niv.app.api.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)
