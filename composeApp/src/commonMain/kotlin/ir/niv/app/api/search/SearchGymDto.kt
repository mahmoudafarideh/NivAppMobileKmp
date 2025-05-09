package ir.niv.app.api.search

import ir.niv.app.di.BaseUrl
import ir.niv.app.domain.core.Avatar
import ir.niv.app.domain.core.Gender
import ir.niv.app.domain.search.SearchGym
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchGymDto(
    @SerialName("username")
    val id: String,
    @SerialName("avatar")
    val avatar: String,
    @SerialName("city")
    val city: CityDto,
    @SerialName("gender_value")
    val gender: GenderDto,
    @SerialName("gender")
    val genderLabel: String,
    @SerialName("name")
    val name: String,
) {
    @Serializable
    data class CityDto(
        @SerialName("city")
        val name: String,
        @SerialName("state")
        val state: String,
    )

}

internal fun SearchGymDto.toDomain() = SearchGym(
    id = id,
    avatar = Avatar(BaseUrl + avatar),
    city = SearchGym.GymCity(name = city.name, state = city.state),
    gender = gender.toGender(),
    name = name,
    genderLabel = genderLabel,
)