package ir.niv.app.ui.core


sealed class DeferredData<out T> {
    abstract val data: T?
    open val isRetrieved: Boolean = false
}

data object Fetching : DeferredData<Nothing>() {
    override val data: Nothing?
        get() = null
}

data class Retrieved<T>(override val data: T) : DeferredData<T>() {
    override val isRetrieved: Boolean
        get() = true
}

data class Refreshing<T>(override val data: T) : DeferredData<T>() {
    override val isRetrieved: Boolean
        get() = true
}

data object ReadyToFetch : DeferredData<Nothing>() {
    override val data: Nothing?
        get() = null
}

data object Failed : DeferredData<Nothing>() {
    override val data: Nothing?
        get() = null
}

val <T> DeferredData<T>.isLoading: Boolean
    get() = this is Fetching || this is Refreshing

val <T> DeferredData<T>.isFailed: Boolean
    get() = this is Failed || this is FailedApi

data class FailedApi(val error: ApiError) : DeferredData<Nothing>() {
    override val data: Nothing?
        get() = null
}

fun <T, R> DeferredData<T>.map(transform: (T) -> R): DeferredData<R> {
    return when (this) {
        is Fetching -> Fetching
        is Retrieved -> Retrieved(transform(data))
        is Refreshing -> Refreshing(transform(data))
        is Failed -> Failed
        is FailedApi -> FailedApi(error)
        is ReadyToFetch -> ReadyToFetch
    }
}

fun <T> DeferredData<T>.onRequestFailure(action: (ApiError) -> Unit) {
    (this as? FailedApi)?.let {
        action(it.error)
    }
}

fun <T> DeferredData<T>.onFailure(action: () -> Unit) {
    (this as? Failed)?.let {
        action()
    }
}

fun <T> DeferredData<T>.onRetrieve(action: (T) -> Unit) {
    (this as? Retrieved)?.let {
        action(it.data)
    }
    (this as? Refreshing)?.let {
        action(it.data)
    }
}
