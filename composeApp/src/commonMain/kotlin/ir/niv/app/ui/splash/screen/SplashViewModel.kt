package ir.niv.app.ui.splash.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.domain.splash.SplashRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.Retrieved
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository,
    private val splashRepository: SplashRepository
) : BaseViewModel<DeferredData<Boolean>>(ReadyToFetch) {

    private val retryClickFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    init {
        checkUserLoggedIn()
        retryClickFlow.tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            combine(
                retryClickFlow,
                userRepository.userFlow
            ) { _, user ->
                user
            }
            .flatMapLatest {
                if (it != null) {
                    getDeferredDataFlow {
                        if (userRepository.userFlow.value != null) {
                            splashRepository.checkLogin()
                            true
                        } else {
                            false
                        }
                    }
                } else {
                    flowOf(Retrieved(false))
                }
            }.collect {
                updateState {
                    it
                }
            }
        }

    }

    fun retryClicked() {
        retryClickFlow.tryEmit(Unit)
    }
}