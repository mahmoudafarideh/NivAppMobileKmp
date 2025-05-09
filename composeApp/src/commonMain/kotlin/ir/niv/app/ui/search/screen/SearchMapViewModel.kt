package ir.niv.app.ui.search.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.search.SearchRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.LatLngUiModel
import ir.niv.app.ui.core.immutableMap
import ir.niv.app.ui.core.toLatLng
import ir.niv.app.ui.search.model.GymMapUiModel
import ir.niv.app.ui.search.model.SearchMapUiModel
import ir.niv.app.ui.search.model.toGymMapUiModel
import ir.niv.app.ui.utils.logInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.*

class SearchMapViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchMapUiModel>(SearchMapUiModel()) {

    private val latLngFlow = MutableSharedFlow<LatLngUiModel>(extraBufferCapacity = 1)
    private val centerFlow = MutableSharedFlow<LatLngUiModel>(extraBufferCapacity = 1)
    private val gymsFlow = MutableStateFlow<ImmutableList<GymMapUiModel>>(persistentListOf())
    val gyms = gymsFlow.asStateFlow()
    val center = centerFlow.asSharedFlow()
    private var userLocation: LatLngUiModel? = null
    private var latestLocation: LatLngUiModel? = null

    init {
        getNearbyGyms()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun getNearbyGyms() {
        viewModelScope.launch {
            latLngFlow.debounce(1_000)
                .flatMapLatest {
                    if (anyGymSelected()) {
                        return@flatMapLatest flowOf()
                    }
                    latestLocation?.let { location ->
                        if(location.distance(it) < 500) {
                            return@flatMapLatest flowOf()
                        }
                    }
                    latestLocation = it
                    getDeferredDataFlow(
                        action = {
                            searchRepository
                                .searchOnMap(it.toLatLng())
                                .gyms.map { gym ->
                                    gym.toGymMapUiModel()
                                }.toImmutableList()
                        }
                    )
                }.collectLatest {
                    it.data?.let { gyms ->
                        gymsFlow.value = gyms
                    }
                }
        }
    }

    fun cameraIdled(latLng: LatLngUiModel) {
        if (anyGymSelected()) return
        latLngFlow.tryEmit(latLng)
        updateState {
            copy(center = latLng)
        }
    }

    private fun anyGymSelected(): Boolean = gyms.value.any { it.selected }

    fun userLocationChanged(lngUiModel: LatLngUiModel) {
        userLocation = lngUiModel
        updateState {
            copy(showMyLocation = true)
        }
    }

    fun userLocationClicked() {
        val location = userLocation ?: return
        centerFlow.tryEmit(location)
    }

    fun markerClicked(gymMapUiModel: GymMapUiModel) {
        gymsFlow.update {
            it.immutableMap { gym ->
                gym.copy(selected = gym.id == gymMapUiModel.id)
            }
        }
        centerFlow.tryEmit(gymMapUiModel.latLng)
    }

    fun screenResumed() {
        gymsFlow.update {
            it.immutableMap { gym ->
                gym.copy(selected = false)
            }
        }
    }

    private fun toRadians(degrees: Double): Double = degrees * (PI / 180.0)
    private fun LatLngUiModel.distance(
        latLng: LatLngUiModel
    ): Double {
        val earthRadius = 6371000

        val dLat = toRadians(lat - latLng.lat)
        val dLon = toRadians(lng - latLng.lng)

        val a = sin(dLat / 2).pow(2.0) +
                cos(toRadians(lat)) * cos(toRadians(latLng.lat)) *
                sin(dLon / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
}