package ir.niv.app.ui

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.ui.core.BaseViewModel
import kotlinx.coroutines.launch

class AppViewModel(
    private val userRepository: UserRepository
): BaseViewModel<Boolean>(userRepository.userFlow.value != null) {
    init {
        observeUserState()
    }

    private fun observeUserState() {
        viewModelScope.launch {
            userRepository.userFlow.collect {
                updateState { it != null }
            }
        }
    }
}