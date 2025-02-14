package ir.niv.app.ui.splash.screen

import ir.niv.app.domain.splash.LoggedInUserRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import kotlinx.coroutines.delay

class SplashViewModel(
    private val loggedInUserRepository: LoggedInUserRepository
) : BaseViewModel<DeferredData<Boolean>>(ReadyToFetch) {

    init {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        getDeferredData(
            action = {
                delay(2_000)
                loggedInUserRepository.id > 0
            },
            currentState = state.value
        ) {
            updateState { it }
        }
    }
}