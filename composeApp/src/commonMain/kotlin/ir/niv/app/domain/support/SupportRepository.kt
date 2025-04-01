package ir.niv.app.domain.support

import ir.niv.app.api.support.TicketsApi
import ir.niv.app.api.support.toTicketDetail
import ir.niv.app.api.support.toTicketMessage
import ir.niv.app.api.support.toUserTicketList

class SupportRepository(
    private val ticketsApi: TicketsApi
) {
    private var _categories: List<TicketCategory> = emptyList()
    val categories get() = _categories
    suspend fun getTickets(page: Int) = ticketsApi.getTickets(page).toUserTicketList().also {
        _categories = it.categories
    }

    suspend fun submitTicket(category: Long, subject: String, message: String) =
        ticketsApi.submitNewTicket(
            category = category,
            subject = subject,
            message = message
        ).insertId

    suspend fun sendMessage(id: Long, message: String) =
        ticketsApi.sendMessage(id, message).toTicketMessage()

    suspend fun getTicket(id: Long) = ticketsApi.getTicket(id).ticket.toTicketDetail()
}