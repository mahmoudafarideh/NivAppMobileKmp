package ir.niv.app.domain.support

data class UserTicketList(
    val tickets: List<UserTicket>,
    val categories: List<TicketCategory>,
)