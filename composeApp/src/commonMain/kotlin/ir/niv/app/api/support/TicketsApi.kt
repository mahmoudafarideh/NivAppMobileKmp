package ir.niv.app.api.support

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.http.parameters
import ir.niv.app.api.core.FormSubmitDto
import ir.niv.app.di.ApiV1

private const val TicketsUrl = ApiV1 + "tickets/?page="
private const val AddTicketUrl = ApiV1 + "addticket/"
private const val AddTicketMessageUrl = ApiV1 + "addticketmessage/"
private const val TicketDetailsUrl = ApiV1 + "ticket/"

class TicketsApi(private val client: HttpClient) {

    suspend fun getTickets(page: Int): UserTicketListDto = client
        .get(urlString = TicketsUrl + page).body()

    suspend fun submitNewTicket(
        category: Long,
        subject: String,
        message: String
    ): FormSubmitDto = client
        .submitForm(
            url = AddTicketUrl,
            formParameters = parameters {
                append("category", "$category")
                append("subject", subject)
                append("message", message)
            }
        ).body()

    suspend fun getTicket(id: Long): TicketDetailsResponseDto = client
        .get(urlString = "$TicketDetailsUrl$id/").body()

    suspend fun sendMessage(id: Long, message: String): TicketMessageDto = client
        .submitForm(
            url = AddTicketMessageUrl,
            formParameters = parameters {
                append("id", "$id")
                append("message", message)
            }
        ).body()
}