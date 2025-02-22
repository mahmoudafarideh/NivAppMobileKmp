package ir.niv.app.ui.login.screen

import ir.niv.app.domain.core.PhoneNumber
import ir.niv.app.domain.login.LoginRepository
import ir.niv.app.domain.login.Otp
import ir.niv.app.domain.login.PhoneNumberRegistrationState
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.login.screen.LoginUiModel.ButtonUiModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel<LoginUiModel>(LoginUiModel()) {

    private val navigateToHomeFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToHome = navigateToHomeFlow.asSharedFlow()

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
        if (state.value.buttonUiModel == ButtonUiModel.RequestOtp) {
            otpRequest(phoneNumber)
        } else {
            val otpCode = state.value.otp.takeIf {
                state.value.otpValid
            } ?: return
            loginWithOtp(phoneNumber, otpCode)
        }
    }

    private fun otpRequest(phoneNumber: String) {
        getDeferredData(
            currentState = state.value.submitState,
            action = {
                val registrationState = loginRepository.checkNumberRegistration(phoneNumber)
                updateState {
                    copy(
                        buttonUiModel = when (registrationState) {
                            PhoneNumberRegistrationState.Registered -> ButtonUiModel.Login
                            PhoneNumberRegistrationState.Unregistered -> ButtonUiModel.Signup
                        }
                    )
                }
            },
            data = {
                updateState { copy(submitState = it) }
            }
        )
    }

    private fun loginWithOtp(phoneNumber: String, otp: String) {
        getDeferredData(
            currentState = state.value.submitState,
            action = {
                loginRepository.loginUsingOtp(PhoneNumber(phoneNumber), Otp(otp))
                navigateToHomeFlow.emit(Unit)
            },
            data = {
                updateState { copy(submitState = it) }
            }
        )
    }

    private fun String.getValidNumber(): String = filter { it.isDigit() }.take(11)

    private fun String.toValidOtp(): String = filter { it.isDigit() }.take(6)

    fun editNumberClicked() {
        updateState {
            copy(
                buttonUiModel = ButtonUiModel.RequestOtp,
                otp = ""
            )
        }
    }

}