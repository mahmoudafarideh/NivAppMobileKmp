package ir.niv.app.api.support

import ir.niv.app.domain.core.StatusModel
import ir.niv.app.domain.support.UserTicket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTicketDto(
    @SerialName("id")
    val id: Long,
    @SerialName("subject")
    val title: String,
    @SerialName("status")
    val status: String,
    @SerialName("status_class")
    val statusType: String,
)

internal fun UserTicketDto.toUserTicket() = UserTicket(
    id = id,
    title = title,
    status = statusType.toStatusModel(status)
)

private fun String.toStatusModel(label: String) = when (this) {
    "success" -> StatusModel.State.Success
    "warning" -> StatusModel.State.Warning
    "primary" -> StatusModel.State.Primary
    "danger" -> StatusModel.State.Danger
    else -> StatusModel.State.Primary
}.let {
    StatusModel(label, it)
}