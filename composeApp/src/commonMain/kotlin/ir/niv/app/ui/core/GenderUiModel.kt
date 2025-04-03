package ir.niv.app.ui.core

import ir.niv.app.domain.core.Gender
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

internal fun Gender.toUiModel() = when (this) {
    Gender.Men -> GenderUiModel.Men
    Gender.Women -> GenderUiModel.Women
    Gender.Both -> GenderUiModel.Both
}