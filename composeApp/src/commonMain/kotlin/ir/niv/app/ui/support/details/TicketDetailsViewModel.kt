package ir.niv.app.ui.support.details

import ir.niv.app.domain.support.SupportRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.map
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay

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
                delay(2_000)
                supportRepository.getTicket(id).toTicketDetailsUiModel()
            },
            data = {
                updateState { it }
            }
        )
    }

    fun sendMessageClicked(message: String) {
        getDeferredData(
            currentState = ReadyToFetch,
            action = {
                val message = supportRepository.sendMessage(id, message)
                updateState {
                    map {
                        it.copy(
                            messages = listOf(message.toTicketMessageUiModel())
                                .plus(it.messages)
                                .toImmutableList()
                        )
                    }
                }
            },
            data = {}
        )
    }
}