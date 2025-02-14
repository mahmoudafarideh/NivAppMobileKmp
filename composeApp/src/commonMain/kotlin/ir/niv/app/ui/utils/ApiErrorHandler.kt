package ir.niv.app.ui.utils

import ir.niv.app.ui.core.ApiError


/**
 * Trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns [ApiError]
 *
 * */
fun traceErrorException(throwable: Throwable?): ApiError {
    val errorStatus: ApiError.ErrorStatus
    var inputErrors: Map<String, String>? = null
    var errorCode = 0
    val message: String = throwable?.message ?: "UNKNOWN_ERROR_MESSAGE"
    var toast: ApiError.Toast? = null

    TODO()
//    when (throwable) {
//        is HttpException -> {
//            val errorJson = getErrorBody(throwable.response())
//            errorJson?.let {
//                toast = getToastMessage(it)
//                inputErrors = getInputErrors(it)
//            }
//            errorStatus = getErrorStatus(throwable.code())
//            errorCode = throwable.code()
//        }
//        is SocketTimeoutException -> {
//            errorStatus = ApiError.ErrorStatus.TIMEOUT
//        }
//        is IOException -> {
//            errorStatus = ApiError.ErrorStatus.NO_CONNECTION
//        }
//        else -> {
//            errorStatus = ApiError.ErrorStatus.UNKNOWN_ERROR
//        }
//    }
//    return ApiError(message, errorCode, errorStatus, toast, inputErrors)
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

//private fun getErrorBody(response: Response<*>?): JSONObject? {
//    try {
//        val responseStr = response?.errorBody()?.string() ?: return null
//        return JSONObject(responseStr)
//    } catch (e: JSONException) {
//        return null
//    }
//}
//
//private fun getToastMessage(errorJson: JSONObject): ApiError.Toast? {
//    if (errorJson.has("toast")) {
//        val toast = errorJson.getJSONObject("toast")
//        return ApiError.Toast(
//            toast.getString("message").localized()!!,
//            when (toast.getString("toast_class").lowercase()) {
//                "success" -> ApiError.ToastType.SUCCESS
//                "danger" -> ApiError.ToastType.DANGER
//                "warning" -> ApiError.ToastType.WARNING
//                else -> ApiError.ToastType.DEFAULT
//            }
//        )
//    }
//    return null
//}
//
//private fun getInputErrors(errorJson: JSONObject): Map<String, String>? {
//    val errors = mutableMapOf<String, String>()
//    if (errorJson.has("errors")) {
//        val jsonErrors: JSONObject = errorJson.getJSONObject("errors")
//        val keys = jsonErrors.keys()
//        while (keys.hasNext()) {
//            val key = keys.next()
//            if (jsonErrors[key] is JSONArray) {
//                var error: String? = null
//                for (i in 0 until jsonErrors.getJSONArray(key).length()) {
//                    error = jsonErrors.getJSONArray(key).getString(i)
//                    break
//                }
//                if (error != null) {
//                    errors[key] = error
//                }
//            }
//        }
//    }
//    return if (errors.isNotEmpty()) errors else null
//}