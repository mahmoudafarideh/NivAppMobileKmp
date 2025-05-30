package ir.niv.app.ui.profile.crop

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.profile.crop.CropRepository
import ir.niv.app.ui.core.BaseViewModel
import kotlinx.coroutines.launch

class CropViewModel(
    val cropRepository: CropRepository
) : BaseViewModel<ImageBitmap?>(null) {
    init {
        viewModelScope.launch {
            cropRepository.getImage()?.let {
                updateState { it }
            }
        }
    }
}