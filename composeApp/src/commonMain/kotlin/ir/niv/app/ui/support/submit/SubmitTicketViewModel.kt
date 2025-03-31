package ir.niv.app.ui.support.submit

import ir.niv.app.domain.support.SupportRepository
import ir.niv.app.ui.core.BaseViewModel
import kotlinx.collections.immutable.toImmutableList

class SubmitTicketViewModel(
    private val supportRepository: SupportRepository
) : BaseViewModel<SubmitTicketUiModel>(
    submitTicketUiModel(supportRepository)
) {

    fun subjectChanged(message: String) {
        updateState {
            copy(subject = message)
        }
    }

    fun messageChanged(message: String) {
        updateState {
            copy(message = message)
        }
    }

    fun categorySelected(id: Long) {
        updateState {
            copy(categories = categories.map { it.copy(selected = it.id == id) }.toImmutableList())
        }
    }

    fun submitButtonClicked() {
        val category = currentState.categories.firstOrNull { it.selected }?.id ?: return
        getDeferredData(
            currentState = currentState.state,
            action = {
                val ticketId = supportRepository.submitTicket(
                    category = category,
                    subject = currentState.subject,
                    message = currentState.message
                )
                Unit
            },
            data = {
                updateState { copy(state = it) }
            }
        )
    }
}

private fun submitTicketUiModel(supportRepository: SupportRepository): SubmitTicketUiModel =
    SubmitTicketUiModel(
        supportRepository.categories.mapIndexed { index, item ->
            SubmitTicketUiModel.CategoryUiModel(item.id, item.name, index == 0)
        }.toImmutableList()
    )