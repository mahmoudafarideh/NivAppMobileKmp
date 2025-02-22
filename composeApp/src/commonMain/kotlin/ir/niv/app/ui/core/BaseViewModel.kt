package ir.niv.app.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.niv.app.ui.utils.traceErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<T>(
    initialState: T
) : ViewModel() {

    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _responseUiState = MutableStateFlow(ResponseUiState())
    val responseUiState = _responseUiState.asStateFlow()

    private val _toastUiStateFlow = MutableSharedFlow<ToastUiState>(extraBufferCapacity = 1)
    val toastUiStateFlow = _toastUiStateFlow.asSharedFlow()

    protected fun updateState(action: T.() -> T) {
        _state.update {
            it.action()
        }
    }

    fun apiErrors(inputName: String) = responseUiState.map {
        it.inputErrors?.get(inputName)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1_000), null)

    protected fun <T> getDeferredData(
        currentState: DeferredData<T>,
        action: suspend () -> T,
        data: (DeferredData<T>) -> Unit
    ) {
        if (currentState.isLoading) return
        data(Fetching)
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                _responseUiState.update {
                    it.copy(inputErrors = null)
                }
                action()
            }.onSuccess {
                data(Retrieved(it))
            }.onFailure {
                val apiError = traceErrorException(it)
                apiError.errors?.let { errors ->
                    _responseUiState.update {
                        it.copy(inputErrors = errors.takeIf { it.isNotEmpty() })
                    }
                }
                apiError.toast?.let {
                    _toastUiStateFlow.tryEmit(it.toUiModel())
                }
                data(FailedApi(apiError))
            }
        }
    }

    protected suspend fun <T> getDeferredDataFlow(
        action: suspend () -> T,
    ): Flow<DeferredData<T>> {
        return flow {
            runCatching {
                action()
            }.onSuccess {
                emit(Retrieved(it))
            }.onFailure {
                val apiError = traceErrorException(it)
                emit(FailedApi(apiError))
            }
        }
    }
}