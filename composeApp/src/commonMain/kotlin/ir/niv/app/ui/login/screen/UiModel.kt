package ir.niv.app.ui.login.screen

import ir.niv.app.domain.login.PhoneNumberRegistrationState
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.isLoading
import ir.niv.app.ui.theme.button.NivButtonState

data class LoginUiModel(
    val phoneNumber: String = "",
    val submitState: DeferredData<PhoneNumberRegistrationState> = ReadyToFetch,
    val buttonUiModel: ButtonUiModel = ButtonUiModel.RequestOtp
) {
    val numberValid = phoneNumber.length == 11
    val buttonState = when {
        !numberValid -> NivButtonState.Disable
        submitState.isLoading -> NivButtonState.Loading
        else -> NivButtonState.Enable
    }

    enum class ButtonUiModel {
        Login,
        Signup,
        RequestOtp
    }
}