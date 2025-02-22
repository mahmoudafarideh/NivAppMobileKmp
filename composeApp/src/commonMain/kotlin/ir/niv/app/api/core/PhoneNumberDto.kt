package ir.niv.app.api.core

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class PhoneNumberDto(val phone: String)
