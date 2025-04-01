package ir.niv.app.ui.support.details

import ir.niv.app.domain.support.SupportRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch

class TicketDetailsViewModel(
    private val id: Long,
    private val supportRepository: SupportRepository
) : BaseViewModel<DeferredData<TicketDetailsUiModel>>(ReadyToFetch) {

    init {
        getTicket()
    }

    private fun getTicket() {
        getDeferredData(
            currentState = currentState,
            action = {
                supportRepository.getTicket(id).toTicketDetailsUiModel()
            },
            data = {
                updateState { it }
            }
        )
    }
}