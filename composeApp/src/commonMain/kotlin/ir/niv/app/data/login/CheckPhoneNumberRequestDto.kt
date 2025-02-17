package ir.niv.app.data.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckPhoneNumberRequestDto(
    @SerialName("phone_number")
    val phone: String
)