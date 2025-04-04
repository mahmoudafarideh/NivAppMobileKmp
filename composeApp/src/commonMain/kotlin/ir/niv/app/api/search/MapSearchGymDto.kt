package ir.niv.app.api.search

import ir.niv.app.di.BaseUrl
import ir.niv.app.domain.core.Avatar
import ir.niv.app.domain.core.LatLng
import ir.niv.app.domain.search.MapSearchGym
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapSearchGymDto(
    @SerialName("username")
    val id: String,
    @SerialName("avatar")
    val avatar: String,
    @SerialName("gender_value")
    val gender: GenderDto,
    @SerialName("gender")
    val genderLabel: String,
    @SerialName("name")
    val name: String,
    @SerialName("open")
    val open: Boolean,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lng: Double,
)

internal fun MapSearchGymDto.toDomain() = MapSearchGym(
    id = id,
    avatar = Avatar(BaseUrl + avatar),
    gender = gender.toGender(),
    name = name,
    latLng = LatLng(lat, lng),
    open = open,
    genderLabel = genderLabel
)