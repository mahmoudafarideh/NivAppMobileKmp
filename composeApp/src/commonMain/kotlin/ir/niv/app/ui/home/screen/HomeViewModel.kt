package ir.niv.app.ui.home.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.ui.core.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<HomeViewModel.State>(
    State(userRepository.userFlow.value?.avatar?.avatar)
) {
    data class State(
        val avatar: String? = null
    )

    init {
        viewModelScope.launch {
            userRepository.userFlow.collect {
                updateState {
                    copy(avatar = it?.avatar?.avatar)
                }
            }
        }
    }
}