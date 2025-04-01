package ir.niv.app.api.support

import ir.niv.app.di.BaseUrl
import ir.niv.app.domain.core.Avatar
import ir.niv.app.domain.support.UserTicketMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketMessageDto(
    @SerialName("add_date")
    val date: String,
    @SerialName("text")
    val content: String,
    @SerialName("admin")
    val admin: Admin?,
) {
    @Serializable
    data class Admin(
        @SerialName("avatar")
        val avatar: String,
        @SerialName("full_name")
        val fullName: String
    )
}

internal fun TicketMessageDto.toTicketMessage() = UserTicketMessage(
    content = content,
    date = date,
    sender = admin?.let {
        UserTicketMessage.Sender.Admin(
            avatar = Avatar(BaseUrl + it.avatar),
            fullName = it.fullName
        )
    } ?: UserTicketMessage.Sender.Self
)