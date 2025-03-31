package ir.niv.app.api.support

import ir.niv.app.domain.support.TicketCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TicketCategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

internal fun TicketCategoryDto.toTicketCategory() = TicketCategory(id = id, name = name)