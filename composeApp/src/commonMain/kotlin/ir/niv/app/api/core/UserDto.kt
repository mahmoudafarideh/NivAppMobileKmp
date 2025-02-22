package ir.niv.app.api.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    @SerialName("first_name")
    val firstname: String,
    @SerialName("last_name")
    val lastname: String,
    val phone: PhoneNumberDto,
    val avatar: AvatarDto,
    val username: String,
    @SerialName("gender")
    val isMale: Boolean
)