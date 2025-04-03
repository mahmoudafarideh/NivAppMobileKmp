package ir.niv.app.ui.search.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.search.SearchRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.core.toLatLng
import ir.niv.app.ui.search.model.SearchMapUiModel
import ir.niv.app.ui.search.model.toGymMapUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SearchMapViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchMapUiModel>(SearchMapUiModel()) {

    private val latLngFlow = MutableSharedFlow<LatLngUiModel>(extraBufferCapacity = 1)

    init {
        getNearbyGyms()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun getNearbyGyms() {
        viewModelScope.launch {
            latLngFlow.debounce(1_000)
                .flatMapLatest {
                    getDeferredDataFlow(
                        action = {
                            searchRepository.searchOnMap(it.toLatLng()).gyms.map { gym ->
                                gym.toGymMapUiModel()
                            }.toImmutableList()
                        }
                    )
                }.collectLatest {
                    it.data?.let { gyms ->
                        updateState {
                            copy(gyms = gyms)
                        }
                    }
                }
        }
    }

    fun cameraIdled(latLng: LatLngUiModel) {
        latLngFlow.tryEmit(latLng)
        updateState {
            copy(center = latLng)
        }
    }
}