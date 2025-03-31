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
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import ir.niv.app.api.login.LoginDto
import ir.niv.app.api.login.client_id
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.domain.repository.AuthRepository
import ir.niv.app.ui.core.ApiError
import ir.niv.app.ui.utils.ClientFailedException
import ir.niv.app.ui.utils.logInfo
import ir.niv.app.ui.utils.traceErrorException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.dsl.module

const val BaseUrl = "https://nivapp.ir"
const val BaseApiUrl = "$BaseUrl/user/api/"
const val ApiV1 = BaseApiUrl + "v1/"

val networkModules = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(get<Json>())
                register(
                    ContentType.Text.Html, KotlinxSerializationConverter(get<Json>())
                )
                register(
                    ContentType.Text.Plain, KotlinxSerializationConverter(get<Json>())
                )
            }
            install(DefaultRequest) {
                url(BaseApiUrl)
                headers.append("Accept", "application/json")
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }
            install(HttpRequestRetry) {
                maxRetries = 1
                retryOnServerErrors(maxRetries)
                exponentialDelay()
            }
            install(Auth) {
                val authRepository: AuthRepository = get()
                val userRepository: UserRepository = get()
                bearer {
                    loadTokens {
                        BearerTokens(
                            authRepository.accessToken.orEmpty(),
                            authRepository.refreshToken.orEmpty()
                        )
                    }
                    refreshTokens {
                        runCatching {
                            refreshAccessToken(
                                this.client,
                                authRepository.refreshToken.orEmpty(),
                            )
                        }.fold(
                            onSuccess = { newAccessToken ->
                                authRepository.updateTokens(
                                    newAccessToken.accessToken, newAccessToken.refreshToken
                                )
                                BearerTokens(
                                    newAccessToken.accessToken,
                                    authRepository.refreshToken.orEmpty()
                                )
                            },
                            onFailure = {
                                traceErrorException(it).let { it ->
                                    when (it.errorStatus) {
                                        ApiError.ErrorStatus.BAD_REQUEST,
                                        ApiError.ErrorStatus.UNAUTHORIZED,
                                        ApiError.ErrorStatus.FORBIDDEN,
                                        ApiError.ErrorStatus.METHOD_NOT_ALLOWED -> {
                                            userRepository.clear()
                                        }

                                        else -> {
                                            if (it.code == 200) {
                                                userRepository.clear()
                                            }
                                        }
                                    }
                                }
                                throw it
                            }
                        )
                    }
                }
            }
        }.apply {
            receivePipeline.intercept(HttpReceivePipeline.Before) {
                logInfo("SXO", it.bodyAsText())
                if (it.status.value == 200) {
                    val responseBody = it.bodyAsText()
                    val json = get<Json>().parseToJsonElement(responseBody).jsonObject
                    if (json.contains("ok") && !json.jsonObject["ok"]!!.jsonPrimitive.boolean) {
                        throw ClientFailedException(it, responseBody)
                    }
                }
                this.proceedWith(it)
            }
        }
    }
}

private suspend fun refreshAccessToken(
    client: HttpClient,
    refreshToken: String,
): LoginDto {
    val response: HttpResponse = client.submitForm(
        url = "${ApiV1}login/token/",
        formParameters = parameters {
            append("refresh_token", refreshToken)
            append("client_id", client_id)
            append("grant_type", "refresh_token")
        }
    )
    return response.body()
}