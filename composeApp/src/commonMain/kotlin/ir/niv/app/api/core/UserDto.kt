package ir.niv.app.api.core

import ir.niv.app.domain.core.Avatar
import ir.niv.app.domain.core.PhoneNumber
import ir.niv.app.domain.core.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    @SerialName("first_name")
    val firstname: String,
    @SerialName("last_name")
    val lastname: String,
    val phone: String,
    val avatar: String,
    val username: String,
    @SerialName("gender")
    val isMale: Boolean
)

internal fun UserDto.toUser() = User(
    id = id,
    firstname = firstname,
    lastname = lastname,
    phone = PhoneNumber(phone),
    avatar = Avatar(avatar),
    username = username,
    isMale = isMale
)