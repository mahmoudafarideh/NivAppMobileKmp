package ir.niv.app.api.search

import ir.niv.app.domain.core.Gender
import kotlinx.serialization.SerialName

enum class GenderDto {
    @SerialName("men")
    Men,

    @SerialName("women")
    Women,

    @SerialName("both")
    Both,
}

internal fun GenderDto.toGender() = when (this) {
    GenderDto.Men -> Gender.Men
    GenderDto.Women -> Gender.Women
    GenderDto.Both -> Gender.Both
}