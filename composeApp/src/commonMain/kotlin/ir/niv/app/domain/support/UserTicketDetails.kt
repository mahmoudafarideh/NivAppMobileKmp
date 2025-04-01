package ir.niv.app.domain.support

data class UserTicketDetails(
    val ticket: UserTicket,
    val messages: List<UserTicketMessage>,
    val closed: Boolean,
)