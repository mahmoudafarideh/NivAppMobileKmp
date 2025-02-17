package ir.niv.app.ui.utils

import io.ktor.client.statement.HttpResponse

class ClientFailedException(
    val response: HttpResponse,
    val cachedResponseText: String
) : Exception()