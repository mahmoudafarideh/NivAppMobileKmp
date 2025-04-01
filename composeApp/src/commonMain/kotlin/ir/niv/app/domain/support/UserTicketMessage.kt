package ir.niv.app.domain.support

import ir.niv.app.domain.core.Avatar


data class UserTicketMessage(
    val content: String,
    val date: String,
    val sender: Sender
) {
    sealed class Sender {
        data object Self : Sender()
        data class Admin(
            val avatar: Avatar,
            val fullName: String
        ) : Sender()
    }
}
