package ir.niv.app.data.login

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.http.parameters
import ir.niv.app.di.ApiV1

private const val RegisterNumberUrl = ApiV1 + "signup/"
private const val client_id = "6065UmUbHtXoNLbSAmouTDH6oelPaL7itKM8Js6T"

class LoginApi(private val client: HttpClient) {
    suspend fun registerNumber(phoneNumber: String): NumberRegisterDto = client
        .submitForm(
            url = RegisterNumberUrl,
            formParameters = parameters {
                append("phone_number", phoneNumber)
            },
            block = {
                this.headers {
                    this.append("Accept", "application/json")
                }
            }
        ).body()
}