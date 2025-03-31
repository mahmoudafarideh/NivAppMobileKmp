package ir.niv.app.ui.core

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableMap

@Stable
/**
 * Designed to show different types of errors through error status & message
 *
 * */
data class ApiError(
    private val errorMessage: String?,
    val code: Int?,
    val errorStatus: ErrorStatus,
    val toast: Toast? = null,
    val errors: ImmutableMap<String, String>? = null
) {

    @Stable
    /**
     * Various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * Any case where a parameter is invalid, or a required parameter is missing.
         * This includes the case where no OAuth token is provided and
         * the case where a resource ID is specified incorrectly in a path.
         */
        BAD_REQUEST,

        /**
         * The OAuth token was provided but was invalid.
         */
        UNAUTHORIZED,

        /**
         * The requested information cannot be viewed by the acting user, for example,
         * because they are not friends with the user whose data they are trying to read.
         * It could also indicate privileges or access has been revoked.
         */
        FORBIDDEN,

        /**
         * Endpoint does not exist.
         */
        NOT_FOUND,

        /**
         * Attempting to use POST with a GET-only endpoint, or vice-versa.
         */
        METHOD_NOT_ALLOWED,

        /**
         * The request could not be completed as it is. Use the information included in the response to modify the request and retry.
         */
        CONFLICT,

        /**
         * There is either a bug on our side or there is an outage.
         * The request is probably valid but needs to be retried later.
         */
        INTERNAL_SERVER_ERROR,

        /**
         * Time out  error
         */
        TIMEOUT,

        /**
         * Error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,

        /**
         * When error is not known
         */
        UNKNOWN_ERROR
    }

    @Stable
    enum class ToastType {
        SUCCESS,
        DANGER,
        WARNING,
        DEFAULT
    }

    @Stable
    data class Toast(
        val message: String,
        val status: ToastType = ToastType.DEFAULT
    )

    @Stable
    data class InputError(
        val inputName: String,
        val errorsList: List<String>
    )

    @Stable
    enum class ErrorMessage {
        CHECK_NETWORK_CONNECTION,
        PROBLEM_OCCUR
    }

    @Stable
    val message by lazy {
        when (errorStatus) {
            ErrorStatus.NO_CONNECTION, ErrorStatus.TIMEOUT -> ErrorMessage.CHECK_NETWORK_CONNECTION
            else -> null
        }
    }

    val shouldGoBack: Boolean
        get() = code == 404
}