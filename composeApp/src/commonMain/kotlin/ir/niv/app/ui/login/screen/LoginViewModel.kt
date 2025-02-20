package ir.niv.app.ui.login.screen

import ir.niv.app.domain.login.LoginRepository
import ir.niv.app.domain.login.PhoneNumberRegistrationState
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.onRetrieve

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel<LoginUiModel>(LoginUiModel()) {

    fun phoneNumberChanged(number: String) {
        val validNumber = getValidNumber(number)
        updateState {
            copy(phoneNumber = validNumber)
        }
    }

    fun loginButtonClicked() {
        val phoneNumber = state.value.phoneNumber.takeIf {
            state.value.numberValid
        } ?: return
        getDeferredData(
            currentState = state.value.submitState,
            action = {
                loginRepository.checkNumberRegistration(phoneNumber)
            },
            data = {
                updateState { copy(submitState = it) }
                it.onRetrieve { it ->
                    updateState {
                        copy(
                            buttonUiModel = when (it) {
                                PhoneNumberRegistrationState.Registered -> LoginUiModel.ButtonUiModel.Login
                                PhoneNumberRegistrationState.Unregistered -> LoginUiModel.ButtonUiModel.Signup
                            }
                        )
                    }
                }
            }
        )
    }

    private fun getValidNumber(number: String): String = number
        .filter { it.isDigit() }.take(11)

    fun editNumberClicked() {
        updateState {
            copy(buttonUiModel = LoginUiModel.ButtonUiModel.RequestOtp)
        }
    }

}