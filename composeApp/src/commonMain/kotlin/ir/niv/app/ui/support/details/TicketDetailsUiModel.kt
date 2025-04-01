package ir.niv.app.ui.support.details

import ir.niv.app.domain.support.UserTicketDetails
import ir.niv.app.domain.support.UserTicketMessage
import ir.niv.app.ui.core.StatusUiModel
import ir.niv.app.ui.core.immutableMap
import ir.niv.app.ui.core.toStatusUiModel
import kotlinx.collections.immutable.ImmutableList

data class TicketDetailsUiModel(
    val id: Long,
    val subject: String,
    val status: StatusUiModel,
    val messages: ImmutableList<TicketMessageUiModel>,
    val closed: Boolean
)

data class TicketMessageUiModel(
    val content: String,
    val date: String,
    val sender: Sender
) {
    sealed class Sender {
        data object Self : Sender()
        data class Admin(
            val avatar: String,
            val fullName: String
        ) : Sender()
    }
}

internal fun UserTicketDetails.toTicketDetailsUiModel() = TicketDetailsUiModel(
    id = ticket.id,
    subject = ticket.title,
    status = ticket.status.toStatusUiModel(),
    messages = messages.reversed().immutableMap {
        it.toTicketMessageUiModel()
    },
    closed = closed
)

internal fun UserTicketMessage.toTicketMessageUiModel() = TicketMessageUiModel(
    content = content,
    date = date,
    sender = when (sender) {
        is UserTicketMessage.Sender.Admin -> TicketMessageUiModel.Sender.Admin(
            sender.avatar.avatar, sender.fullName
        )

        UserTicketMessage.Sender.Self -> TicketMessageUiModel.Sender.Self
    }
)