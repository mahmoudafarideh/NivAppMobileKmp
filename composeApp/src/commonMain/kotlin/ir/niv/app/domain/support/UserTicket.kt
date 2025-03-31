package ir.niv.app.domain.support

import ir.niv.app.domain.core.StatusModel

data class UserTicket(
    val id: Long,
    val title: String,
    val status: StatusModel
)