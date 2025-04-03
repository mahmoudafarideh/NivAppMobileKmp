package ir.niv.app.domain.search

import ir.niv.app.domain.core.Avatar
import ir.niv.app.domain.core.Gender
import ir.niv.app.domain.core.LatLng

data class MapSearchGym(
    val id: String,
    val avatar: Avatar,
    val gender: Gender,
    val name: String,
    val latLng: LatLng,
    val open: Boolean
)