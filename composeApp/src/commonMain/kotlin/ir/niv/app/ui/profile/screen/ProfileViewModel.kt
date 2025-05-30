package ir.niv.app.ui.profile.screen

import ir.niv.app.domain.core.UserRepository
import ir.niv.app.domain.profile.crop.CropRepository
import ir.niv.app.ui.core.BaseViewModel

class ProfileViewModel(
    profileRepository: UserRepository,
    val cropRepository: CropRepository
) : BaseViewModel<ProfileUiModel>(
    initialState(profileRepository)
) {
    fun photoSelected(base64: String) {
        cropRepository.setImage(base64)
    }
}

private fun initialState(profileRepository: UserRepository): ProfileUiModel =
    profileRepository.userFlow.value?.let {
        ProfileUiModel(name = it.firstname, lastname = it.lastname, avatar = it.avatar.avatar)
    } ?: ProfileUiModel()