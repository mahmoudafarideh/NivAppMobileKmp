package ir.niv.app.ui.core

import ir.niv.app.domain.search.SearchGym
import nivapp.composeapp.generated.resources.Res
import nivapp.composeapp.generated.resources.fi_sr_people
import nivapp.composeapp.generated.resources.fi_sr_person_dress_simple
import nivapp.composeapp.generated.resources.fi_sr_person_simple
import org.jetbrains.compose.resources.DrawableResource

enum class GenderUiModel(val icon: DrawableResource) {
    Men(Res.drawable.fi_sr_person_simple),
    Women(Res.drawable.fi_sr_person_dress_simple),
    Both(Res.drawable.fi_sr_people)
}

internal fun SearchGym.Gender.toUiModel() = when(this) {
    SearchGym.Gender.Men -> GenderUiModel.Men
    SearchGym.Gender.Women -> GenderUiModel.Women
    SearchGym.Gender.Both -> GenderUiModel.Both
}