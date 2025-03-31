package ir.niv.app.api.support

import ir.niv.app.domain.support.UserTicketList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTicketListDto(
    @SerialName("tickets")
    val tickets: List<UserTicketDto>,
    @SerialName("tickets_category")
    val categories: List<TicketCategoryDto>,
)

internal fun UserTicketListDto.toUserTicketList() = UserTicketList(
    tickets = tickets.map { it.toUserTicket() },
    categories = categories.map { it.toTicketCategory() }
)