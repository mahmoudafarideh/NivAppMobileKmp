package ir.niv.app.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.niv.app.ui.utils.traceErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<T>(
    initialState: T
) : ViewModel() {
    private val _state: MutableStateFlow<T> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    protected fun updateState(action: T.() -> T) {
        _state.update {
            it.action()
        }
    }

    protected fun <T> getDeferredData(
        currentState: DeferredData<T>,
        action: suspend () -> T,
        data: (DeferredData<T>) -> Unit
    ) {
        if(currentState.isLoading) return
        data(Fetching)
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                action()
            }.onSuccess {
                data(Retrieved(it))
            }.onFailure {
                data(FailedApi(traceErrorException(it)))
            }
        }
    }
}