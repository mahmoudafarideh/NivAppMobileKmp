package ir.niv.app.domain.search

import ir.niv.app.api.search.SearchApi
import ir.niv.app.api.search.toDomain

class SearchRepository(
    private val searchApi: SearchApi,
) {
    suspend fun search(query: String, page: Int) = searchApi.search(query, page).toDomain()
}