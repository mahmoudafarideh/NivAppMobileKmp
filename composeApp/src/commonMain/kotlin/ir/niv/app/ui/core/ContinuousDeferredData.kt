package ir.niv.app.ui.core

import androidx.compose.runtime.Stable

@Stable
sealed class ContinuousDeferredData<out T>(
    open val page: Int,
    open val limit: Int
) {
    abstract val data: T?
}

@Stable
class ReadyToInitialFetch(
    page: Int, limit: Int
) : ContinuousDeferredData<Nothing>(page, limit) {
    override val data: Nothing?
        get() = null
}

@Stable
class InitialFetching(
    page: Int, limit: Int
) : ContinuousDeferredData<Nothing>(page, limit) {
    override val data: Nothing?
        get() = null
}

@Stable
data class InitialFailedApi(
    val error: ApiError,
    override val page: Int,
    override val limit: Int
) : ContinuousDeferredData<Nothing>(
    page, limit
) {
    override val data: Nothing?
        get() = null
}

@Stable
data class ContinuousFetching<T>(
    override val data: T,
    override val page: Int,
    override val limit: Int
) : ContinuousDeferredData<T>(page, limit)

@Stable
data class ContinuousRetrieved<T>(
    override val data: T,
    override val page: Int,
    override val limit: Int
) : ContinuousDeferredData<T>(page, limit)

@Stable
data class ContinuousAllRetrieved<T>(
    override val data: T,
    override val page: Int,
    override val limit: Int
) : ContinuousDeferredData<T>(page, limit)

@Stable
data class ContinuousFailedApi<T>(
    override val data: T,
    val error: ApiError,
    override val page: Int,
    override val limit: Int
) : ContinuousDeferredData<T>(
    page, limit
)


fun <T> ContinuousDeferredData<T>.loading(): ContinuousDeferredData<T> {
    return when (this) {
        is ReadyToInitialFetch,
        is InitialFetching,
        is InitialFailedApi -> InitialFetching(
            this.page,
            this.limit
        )

        else -> ContinuousFetching(
            this.data!!,
            this.page,
            this.limit,
        )
    }
}

fun <T> ContinuousDeferredData<T>.retrieved(
    data: T, canContinue: Boolean = true
): ContinuousDeferredData<T> {
    return if (canContinue)
        ContinuousRetrieved(data, this.page + 1, this.limit)
    else
        ContinuousAllRetrieved(data, this.page + 1, this.limit)
}

fun <T> ContinuousDeferredData<T>.failed(
    apiError: ApiError
): ContinuousDeferredData<T> {
    return when (this) {
        is ContinuousFetching -> ContinuousFailedApi(
            this.data, apiError, this.page, this.limit
        )

        else -> InitialFailedApi(
            apiError, this.page, this.limit
        )
    }
}


fun <T> ContinuousDeferredData<T>.copy(
    action: (T) -> T
): ContinuousDeferredData<T> {
    val data = this.data
    return when (this) {
        is ReadyToInitialFetch,
        is InitialFetching,
        is InitialFailedApi -> this

        is ContinuousFetching -> ContinuousFetching(
            action(data!!),
            this.page,
            this.limit
        )

        is ContinuousRetrieved -> ContinuousRetrieved(
            action(data!!),
            this.page,
            this.limit
        )

        is ContinuousAllRetrieved -> ContinuousAllRetrieved(
            action(data!!),
            this.page,
            this.limit
        )

        is ContinuousFailedApi -> ContinuousFailedApi(
            action(data!!),
            this.error,
            this.page,
            this.limit
        )
    }
}


val <T> ContinuousDeferredData<T>.isLoading: Boolean
    get() = this is ContinuousFetching || this is InitialFetching


val <T> ContinuousDeferredData<T>.isFailed: Boolean
    get() = this is InitialFailedApi || this is ContinuousFailedApi

val <T> ContinuousDeferredData<T>.hasData: Boolean
    get() = this.data != null

val <T> ContinuousDeferredData<T>.isEnded: Boolean
    get() = this is ContinuousAllRetrieved

val <T> ContinuousDeferredData<T>.isFirstPageData: Boolean
    get() = this is ContinuousRetrieved && this.page == 2