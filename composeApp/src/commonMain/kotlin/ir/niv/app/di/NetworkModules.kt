package ir.niv.app.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import ir.niv.app.data.login.LoginApi
import ir.niv.app.domain.repository.AuthRepository
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val BaseUrl = "https://nivapp.ir/user/api/"
const val ApiV1 = BaseUrl + "v1/"

val networkModules = module {
    single<HttpClient> {
        val authRepository: AuthRepository = get()
        HttpClient {
            install(ContentNegotiation) {
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
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
                maxRetries = 3
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