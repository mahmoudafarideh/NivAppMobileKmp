package ir.niv.app.api.search

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.parameters
import ir.niv.app.api.core.CheckLoginDto
import ir.niv.app.api.login.LoginDto
import ir.niv.app.api.login.NumberRegisterDto
import ir.niv.app.di.ApiV1
import ir.niv.app.domain.core.PhoneNumber
import ir.niv.app.domain.login.Otp

private const val SearchUrl = ApiV1 + "search/"

class SearchApi(private val client: HttpClient) {

    suspend fun search(
        query: String, page: Int
    ): SearchResultDto = client
        .get(urlString = SearchUrl) {
            this.parameter("q", query)
            this.parameter("page", page)
        }.body()

}