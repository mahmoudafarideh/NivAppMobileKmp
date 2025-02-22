package ir.niv.app.domain.core

data class User(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val phone: PhoneNumber,
    val avatar: Avatar,
    val username: String,
    val isMale: Boolean
)
