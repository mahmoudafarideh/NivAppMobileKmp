package ir.niv.app.domain.search

import ir.niv.app.domain.core.State

data class SearchResult(
    val selectedCity: Long,
    val filters: List<SearchFilter>,
    val states: List<State>,
    val gyms: List<SearchGym>,
    val hasNext: Boolean,
)
