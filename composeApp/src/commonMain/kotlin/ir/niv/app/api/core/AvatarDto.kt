package ir.niv.app.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class AvatarDto(
    val avatar: String
)