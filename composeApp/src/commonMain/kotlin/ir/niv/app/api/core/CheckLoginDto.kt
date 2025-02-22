package ir.niv.app.api.core

import kotlinx.serialization.Serializable

@Serializable
data class CheckLoginDto(
    val user: UserDto
)