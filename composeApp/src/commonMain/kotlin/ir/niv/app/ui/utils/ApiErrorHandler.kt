package ir.niv.app.ui.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.bodyAsText
import ir.niv.app.ui.core.ApiError
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


/**
 * Trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns [ApiError]
 *
 * */
suspend fun traceErrorException(throwable: Throwable?): ApiError {
    val errorStatus: ApiError.ErrorStatus
    var inputErrors: Map<String, String>? = null
    var errorCode = 0
    val message: String = throwable?.message ?: "UNKNOWN_ERROR_MESSAGE"
    var toast: ApiError.Toast? = null
    when (throwable) {
        is ClientRequestException -> {
            val errorJson = getErrorBody(throwable.response.bodyAsText())
            errorJson?.let {
                toast = getToastMessage(it)
                inputErrors = getInputErrors(it)
            }
            errorStatus = getErrorStatus(throwable.response.status.value)
            errorCode = throwable.response.status.value
        }

        is ClientFailedException -> {
            val errorJson = getErrorBody(throwable.cachedResponseText)
            errorJson?.let {
                toast = getToastMessage(it)
                inputErrors = getInputErrors(it)
            }
            errorStatus = getErrorStatus(throwable.response.status.value)
            errorCode = throwable.response.status.value
        }

        is HttpRequestTimeoutException -> {
            errorStatus = ApiError.ErrorStatus.TIMEOUT
        }

        is IOException -> {
            errorStatus = ApiError.ErrorStatus.NO_CONNECTION
        }

        else -> {
            errorStatus = ApiError.ErrorStatus.UNKNOWN_ERROR
        }
    }
    return ApiError(message, errorCode, errorStatus, toast, inputErrors?.toImmutableMap())
}

private fun getErrorStatus(code: Int): ApiError.ErrorStatus {
    return when (code) {
        400 -> ApiError.ErrorStatus.BAD_REQUEST
        401 -> ApiError.ErrorStatus.UNAUTHORIZED
        403 -> ApiError.ErrorStatus.FORBIDDEN
        404 -> ApiError.ErrorStatus.NOT_FOUND
        405 -> ApiError.ErrorStatus.METHOD_NOT_ALLOWED
        409 -> ApiError.ErrorStatus.CONFLICT
        422 -> ApiError.ErrorStatus.CONFLICT
        500 -> ApiError.ErrorStatus.INTERNAL_SERVER_ERROR
        else -> ApiError.ErrorStatus.UNKNOWN_ERROR
    }
}

private fun getErrorBody(response: String): JsonObject? {
    return try {
        Json.parseToJsonElement(response).jsonObject
    } catch (_: Exception) {
        null
    }
}

@Suppress("MemberExtensionConflict")
private fun getToastMessage(errorJson: JsonObject): ApiError.Toast? {
    if (errorJson.contains("toast")) {
        return ApiError.Toast(
            errorJson.jsonObject["toast_message"]!!.toString().toPersianDigits(),
            when (errorJson.jsonObject["toast_type"]!!.toString().lowercase()) {
                "success" -> ApiError.ToastType.SUCCESS
                "danger" -> ApiError.ToastType.DANGER
                "warning" -> ApiError.ToastType.WARNING
                else -> ApiError.ToastType.DEFAULT
            }
        )
    }
    return null
}

private fun getInputErrors(errorJson: JsonObject): Map<String, String>? {
    val errors = mutableMapOf<String, String>()
    if (errorJson.contains("errors")) {
        val jsonErrors: JsonObject = errorJson["errors"]!!.jsonObject
        val keys = jsonErrors.keys
        keys.forEach { key ->
            if (jsonErrors[key] is JsonArray) {
                var error: String? = null
                for (i in 0 until jsonErrors[key]!!.jsonArray.size) {
                    error = jsonErrors[key]!!.jsonArray[i].jsonPrimitive.content.toPersianDigits()
                    break
                }
                if (error != null) {
                    errors[key] = error
                }
            }
        }
    }
    return errors.ifEmpty { null }
}

fun String.toPersianDigits(): String {
    val englishDigits = "0123456789"
    val persianDigits = "۰۱۲۳۴۵۶۷۸۹"

    return this.map { char ->
        val index = englishDigits.indexOf(char)
        if (index != -1) persianDigits[index] else char
    }.joinToString("")
}
