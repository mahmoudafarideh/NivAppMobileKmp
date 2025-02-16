package ir.niv.app.ui.login.screen

import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.isLoading
import niv.design.designsystem.button.NivButtonState

data class LoginUiModel(
    val phoneNumber: String = "",
    val submitState: DeferredData<Unit> = ReadyToFetch,
) {
    val numberValid = phoneNumber.length == 11
    val buttonState = when {
        !numberValid -> NivButtonState.Disable
        submitState.isLoading -> NivButtonState.Loading
        else -> NivButtonState.Enable
    }
}