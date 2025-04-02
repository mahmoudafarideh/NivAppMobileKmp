package ir.niv.app.domain.core

data class State(
    val id: Long,
    val name: String,
    val cities: List<City>,
)
