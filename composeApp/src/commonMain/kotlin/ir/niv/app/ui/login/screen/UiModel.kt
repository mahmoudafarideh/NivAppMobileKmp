package ir.niv.app.ui.login.screen

import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.isLoading
import ir.niv.app.ui.theme.button.NivButtonState

data class LoginUiModel(
    val phoneNumber: String = "",
    val otp: String = "",
    val submitState: DeferredData<Unit> = ReadyToFetch,
    val buttonUiModel: ButtonUiModel = ButtonUiModel.RequestOtp
) {
    val numberValid = phoneNumber.length == 11
    val otpValid = otp.length == 6

    val buttonState = when (buttonUiModel) {
        ButtonUiModel.RequestOtp -> when {
            !numberValid -> NivButtonState.Disable
            submitState.isLoading -> NivButtonState.Loading
            else -> NivButtonState.Enable
        }

        else -> when {
            !otpValid -> NivButtonState.Disable
            submitState.isLoading -> NivButtonState.Loading
            else -> NivButtonState.Enable
        }
    }

    enum class ButtonUiModel {
        Login,
        Signup,
        RequestOtp
    }
}