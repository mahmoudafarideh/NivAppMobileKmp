package ir.niv.app.ui.home.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.core.UserRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.home.models.HomeGridItemUiModel
import ir.niv.app.ui.home.models.UserUiModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<HomeViewModel.State>(
    State()
) {
    data class State(
        val user: UserUiModel? = null,
        val grid: List<HomeGridItemUiModel> = HomeGridItemUiModel.getItems()
    )

    init {
        viewModelScope.launch {
            userRepository.userFlow.collect {
                updateState {
                    copy(
                        user = it?.let {
                            UserUiModel(it.firstname, it.avatar.avatar)
                        }
                    )
                }
            }
        }
    }
}