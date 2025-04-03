package ir.niv.app.domain.search

data class MapSearchResult(
    val filters: List<SearchFilter>,
    val gyms: List<MapSearchGym>,
)
