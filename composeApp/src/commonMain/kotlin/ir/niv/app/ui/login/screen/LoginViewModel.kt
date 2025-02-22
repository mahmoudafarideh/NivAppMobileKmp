package ir.niv.app.ui.login.screen

import ir.niv.app.domain.login.LoginRepository
import ir.niv.app.domain.login.PhoneNumberRegistrationState
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.onRetrieve
import ir.niv.app.ui.login.screen.LoginUiModel.ButtonUiModel

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel<LoginUiModel>(LoginUiModel()) {

    fun phoneNumberChanged(number: String) {
        updateState {
            copy(phoneNumber = number.getValidNumber())
        }
    }

    fun otpChanged(code: String) {
        updateState {
            copy(otp = code.toValidOtp())
        }
    }

    fun loginButtonClicked() {
        val phoneNumber = state.value.phoneNumber.takeIf {
            state.value.numberValid
        } ?: return
        if(state.value.buttonUiModel == ButtonUiModel.RequestOtp) {
            otpRequest(phoneNumber)
        } else {

        }
    }

    private fun otpRequest(phoneNumber: String) {
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

    private fun String.getValidNumber(): String = filter { it.isDigit() }.take(11)

    private fun String.toValidOtp(): String = filter { it.isDigit() }.take(6)

    fun editNumberClicked() {
        updateState {
            copy(
                buttonUiModel = LoginUiModel.ButtonUiModel.RequestOtp,
                otp = ""
            )
        }
    }

}