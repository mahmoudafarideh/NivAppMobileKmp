package ir.niv.app.ui.support.submit

import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.isLoading
import ir.niv.app.ui.theme.button.NivButtonState
import kotlinx.collections.immutable.ImmutableList

data class SubmitTicketUiModel(
    val categories: ImmutableList<CategoryUiModel>,
    val subject: String = "",
    val message: String = "",
    val state: DeferredData<Long> = ReadyToFetch,
) {
    data class CategoryUiModel(
        val id: Long,
        val name: String,
        val selected: Boolean = false
    )

    val buttonState = when {
        state.isLoading -> NivButtonState.Loading
        subject.isNotBlank() && message.isNotBlank() -> NivButtonState.Enable
        else -> NivButtonState.Disable
    }
}