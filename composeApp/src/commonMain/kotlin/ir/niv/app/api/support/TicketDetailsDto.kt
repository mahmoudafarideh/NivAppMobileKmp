package ir.niv.app.api.support

import ir.niv.app.domain.support.UserTicketDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketDetailsResponseDto(
    @SerialName("ticket")
    val ticket: TicketDetailsDto,
)

@Serializable
data class TicketDetailsDto(
    @SerialName("id")
    val id: Long,
    @SerialName("subject")
    val title: String,
    @SerialName("is_closed")
    val closed: Boolean?,
    @SerialName("status")
    val status: String,
    @SerialName("status_class")
    val statusType: String,
    @SerialName("replies")
    val messages: List<TicketMessageDto>,
)

internal fun TicketDetailsDto.toTicketDetail() = UserTicketDetails(
    ticket = toUserTicketDto().toUserTicket(),
    messages = messages.map { it.toTicketMessage() },
    closed = closed == true
)

private fun TicketDetailsDto.toUserTicketDto() = UserTicketDto(
    id = id,
    title = title,
    status = status,
    statusType = statusType
)
