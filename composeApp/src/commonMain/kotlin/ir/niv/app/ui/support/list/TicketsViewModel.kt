package ir.niv.app.ui.support.list

import ir.niv.app.domain.support.SupportRepository
import ir.niv.app.domain.support.TicketCategory
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.Retrieved
import ir.niv.app.ui.core.isLoading
import kotlinx.collections.immutable.toImmutableList

class TicketsViewModel(
    private val supportRepository: SupportRepository
) : BaseViewModel<TicketsUiModel>(TicketsUiModel()) {

    init {
        getTickets()
    }

    private fun getTickets() {
        getContinuosDeferredData(
            currentState = currentState.tickets,
            action = { page, limit ->
                supportRepository.getTickets(page).let {
                    updateCategories(it.categories)
                    it.tickets.map { it -> it.toTicketUiModel() }.toImmutableList()
                }
            },
            data = {
                updateState { copy(tickets = it) }
            }
        )
    }

    fun listReachedEnd() {
        getTickets()
    }

    fun retryClicked() {
        getTickets()
    }

    private fun updateCategories(categories: List<TicketCategory>) {
        categories.let { it ->
            it.map { it -> it.toTicketCategoryUiModel() }.let { it ->
                updateState {
                    copy(categories = Retrieved(it.toImmutableList()))
                }
            }
        }
    }

    fun refreshRequested() {
        if(currentState.tickets.isLoading) return
        updateState { TicketsUiModel() }
        getTickets()
    }
}