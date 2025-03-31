package ir.niv.app.api.support

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ir.niv.app.di.ApiV1

private const val TicketsUrl = ApiV1 + "tickets/?page="

class TicketsApi(private val client: HttpClient) {

    suspend fun getTickets(page: Int): UserTicketListDto = client
        .get(urlString = TicketsUrl + page).body()
}