package ir.niv.app.api.search

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import ir.niv.app.di.ApiV1
import ir.niv.app.domain.core.LatLng

private const val SearchUrl = ApiV1 + "search/"
private const val MapSearchUrl = ApiV1 + "search/map/"

class SearchApi(private val client: HttpClient) {

    suspend fun search(
        query: String, page: Int
    ): SearchResultDto = client
        .get(urlString = SearchUrl) {
            this.parameter("q", query)
            this.parameter("page", page)
        }.body()

    suspend fun searchOnMap(
        latLng: LatLng
    ): MapSearchResultDto = client
        .submitForm(
            url = MapSearchUrl,
            formParameters = parameters {
                append("lat", latLng.lat.toString())
                append("lon", latLng.lng.toString())
            }
        ).body()

}