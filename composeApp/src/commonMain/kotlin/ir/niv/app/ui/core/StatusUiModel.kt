package ir.niv.app.ui.core

import androidx.compose.runtime.Composable
import ir.niv.app.domain.core.StatusModel
import ir.niv.app.ui.theme.theme.LocalExtendedColorScheme
import ir.niv.app.ui.theme.theme.NivTheme

class StatusUiModel(
    val label: String,
    val state: State
) {
    enum class State {
        Success,
        Warning,
        Primary,
        Danger
    }

    val backgroundColor
        @Composable get() = when (state) {
            State.Success -> LocalExtendedColorScheme.current.success.colorContainer
            State.Warning -> NivTheme.colorScheme.errorContainer
            State.Primary -> NivTheme.colorScheme.primaryContainer
            State.Danger -> NivTheme.colorScheme.errorContainer
        }

    val contentColor
        @Composable get() = when (state) {
            State.Success -> LocalExtendedColorScheme.current.success.onColorContainer
            State.Warning -> NivTheme.colorScheme.onErrorContainer
            State.Primary -> NivTheme.colorScheme.onPrimaryContainer
            State.Danger -> NivTheme.colorScheme.onErrorContainer
        }
}

internal fun StatusModel.toStatusUiModel() = when (this.state) {
    StatusModel.State.Success -> StatusUiModel.State.Success
    StatusModel.State.Warning -> StatusUiModel.State.Warning
    StatusModel.State.Primary -> StatusUiModel.State.Primary
    StatusModel.State.Danger -> StatusUiModel.State.Danger
}.let {
    StatusUiModel(label = label, state = it)
}