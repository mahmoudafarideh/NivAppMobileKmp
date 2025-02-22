package ir.niv.app.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import ir.niv.app.api.login.LoginApi
import ir.niv.app.domain.repository.AuthRepository
import org.koin.dsl.module

const val BaseUrl = "https://nivapp.ir/user/api/"
const val ApiV1 = BaseUrl + "v1/"

val networkModules = module {
    single<HttpClient> {
        val authRepository: AuthRepository = get()
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }
            install(DefaultRequest) {
                url(BaseUrl)
                headers.append("Accept", "application/json")
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(HttpRequestRetry) {
                maxRetries = 1
                retryOnServerErrors(maxRetries)
                exponentialDelay()
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            authRepository.accessToken.orEmpty(),
                            authRepository.refreshToken.orEmpty()
                        )
                    }
                    refreshTokens {
                        val newAccessToken = refreshAccessToken(this.client)
                        BearerTokens(newAccessToken, authRepository.refreshToken.orEmpty())
                    }
                }
            }
//            install(HttpCallValidator) {
//                validateResponse { response ->
//                    val responseBody = response.bodyAsText()
//                    val json = Json.parseToJsonElement(responseBody).jsonObject
//                    if (json.contains("ok") && !json.jsonObject["ok"]!!.jsonPrimitive.boolean) {
//                        throw ClientFailedException(response, responseBody)
//                    }
//                }
//            }
        }
    }
    single { LoginApi(get()) }
}

private suspend fun refreshAccessToken(client: HttpClient): String {
    val response: HttpResponse = client.post("$BaseUrl/auth/refresh") {
        header("Authorization", "Bearer YOUR_REFRESH_TOKEN")
    }
    return response.body()
}