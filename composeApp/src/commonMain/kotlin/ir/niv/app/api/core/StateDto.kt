package ir.niv.app.api.core

import ir.niv.app.domain.core.State
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)

internal fun StateDto.toDomain() = State(
    id = id,
    name = name,
    cities = listOf()
)