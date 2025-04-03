package ir.niv.app.domain.search

import ir.niv.app.api.search.SearchApi
import ir.niv.app.api.search.toDomain
import ir.niv.app.domain.core.LatLng

class SearchRepository(
    private val searchApi: SearchApi,
) {
    suspend fun search(query: String, page: Int) = searchApi.search(query, page).toDomain()
    suspend fun searchOnMap(latLng: LatLng) = searchApi.searchOnMap(latLng).toDomain()
}