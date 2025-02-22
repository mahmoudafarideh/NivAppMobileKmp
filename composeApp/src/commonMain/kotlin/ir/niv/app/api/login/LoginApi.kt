package ir.niv.app.api.login

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import ir.niv.app.di.ApiV1
import ir.niv.app.domain.core.PhoneNumber
import ir.niv.app.domain.login.Otp

private const val RegisterNumberUrl = ApiV1 + "signup/"
private const val LoginOtpUrl = ApiV1 + "login/otp/"
private const val client_id = "6065UmUbHtXoNLbSAmouTDH6oelPaL7itKM8Js6T"

class LoginApi(private val client: HttpClient) {

    suspend fun registerNumber(phoneNumber: String): NumberRegisterDto = client
        .submitForm(
            url = RegisterNumberUrl,
            formParameters = parameters {
                append("phone_number", phoneNumber)
            }
        ).body()

    suspend fun login(phoneNumber: PhoneNumber, otpCode: Otp): LoginDto = client
        .submitForm(
            url = LoginOtpUrl,
            formParameters = parameters {
                append("phone_number", phoneNumber.phone)
                append("generated_code", otpCode.code)
                append("client_id", client_id)
                append("grant_type", "otp")
            }
        ).body()
}