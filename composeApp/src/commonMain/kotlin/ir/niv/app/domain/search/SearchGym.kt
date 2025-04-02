package ir.niv.app.domain.search

import ir.niv.app.domain.core.Avatar
import kotlinx.serialization.SerialName

data class SearchGym(
    val id: String,
    val avatar: Avatar,
    val city: GymCity,
    val gender: Gender,
    val genderLabel: String,
    val name: String,
) {
    data class GymCity(
        val name: String,
        val state: String,
    )

    enum class Gender {
        Men,
        Women,
        Both,
    }
}
