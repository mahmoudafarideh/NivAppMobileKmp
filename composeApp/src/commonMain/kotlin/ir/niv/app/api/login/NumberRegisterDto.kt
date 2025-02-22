package ir.niv.app.api.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NumberRegisterDto(
    @SerialName("response")
    val state: StateDto,
    @SerialName("timeout")
    val timeout: Long = 120_000
) {
    @Serializable
    enum class StateDto {
        @SerialName("login")
        Login,

        @SerialName("verify_phone")
        VerifyPhone
    }
}