package ir.niv.app.ui.core

import ir.niv.app.ui.core.ToastUiState.ToastTypeUiModel

data class ToastUiState(
    val message: String,
    val type: ToastTypeUiModel
) {
    enum class ToastTypeUiModel {
        Success,
        Warning,
        Danger,
        Neutral
    }
}

internal fun ApiError.Toast.toUiModel() = ToastUiState(
    message = message,
    type = when (this.status) {
        ApiError.ToastType.SUCCESS -> ToastTypeUiModel.Success
        ApiError.ToastType.DANGER -> ToastTypeUiModel.Danger
        ApiError.ToastType.WARNING -> ToastTypeUiModel.Warning
        ApiError.ToastType.DEFAULT -> ToastTypeUiModel.Neutral
    }
)