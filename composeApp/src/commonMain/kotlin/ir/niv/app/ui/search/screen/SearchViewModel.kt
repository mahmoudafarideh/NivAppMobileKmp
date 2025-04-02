package ir.niv.app.ui.search.screen

import androidx.lifecycle.viewModelScope
import ir.niv.app.domain.search.SearchRepository
import ir.niv.app.ui.core.BaseViewModel
import ir.niv.app.ui.core.ReadyToInitialFetch
import ir.niv.app.ui.core.immutableMap
import ir.niv.app.ui.utils.logInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchUiModel>(SearchUiModel()) {

    private val _getDataFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    init {
        searchGyms()
    }

    private fun searchGyms() {
        viewModelScope.launch {
            state
                .map { it.query.takeIf { query -> query.length >= 2 } }
                .distinctUntilChanged().map {
                    updateState {
                        copy(gyms = ReadyToInitialFetch(1, 5))
                    }
                    it
                }
                .debounce(500)
                .flatMapLatest {
                    if(it == null) return@flatMapLatest flowOf()
                    getContinuosDeferredDataFlow(
                        currentState = currentState.gyms,
                        action = { page, limit ->
                            delay(2_000)
                            searchRepository.search(query = it, page = page)
                        },
                        hasNext = { searchResult ->
                            searchResult.hasNext
                        },
                        transform = { searchResult ->
                            searchResult.gyms.immutableMap { gym -> gym.toUiModel() }
                        },
                        retryFlow = _getDataFlow,
                        reachEndFlow = _getDataFlow
                    )
                }.collectLatest {
                    updateState {
                        copy(gyms = it)
                    }
                }
        }
    }

    fun queryChanged(query: String) {
        updateState {
            copy(query = query)
        }
    }

}