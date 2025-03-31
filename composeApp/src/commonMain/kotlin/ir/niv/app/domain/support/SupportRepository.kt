package ir.niv.app.domain.support

import ir.niv.app.api.support.TicketsApi
import ir.niv.app.api.support.toUserTicketList

class SupportRepository(
    private val ticketsApi: TicketsApi
) {
    suspend fun getTickets(page: Int) = ticketsApi.getTickets(page).toUserTicketList()
}