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
    @SerialName("has_next")
    val hasNext: Boolean,
)

internal fun UserTicketListDto.toUserTicketList() = UserTicketList(
    tickets = tickets.map { it.toUserTicket() },
    categories = categories.map { it.toTicketCategory() },
    hasNext = hasNext
)