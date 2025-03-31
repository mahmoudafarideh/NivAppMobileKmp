package ir.niv.app.api.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormSubmitDto(
    @SerialName("insert_id")
    val insertId: Long
)
