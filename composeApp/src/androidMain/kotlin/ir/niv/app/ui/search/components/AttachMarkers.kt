package ir.niv.app.ui.search.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import ir.niv.app.R
import ir.niv.app.ui.search.model.GymMapUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.annotation.Symbol
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions

@Composable
internal fun AttachMarkers(
    symbolManager: MutableState<Pair<Style, SymbolManager>?>,
    markers: Flow<ImmutableList<GymMapUiModel>>,
    context: Context,
    attachedMarkers: MutableState<List<Pair<GymMapUiModel, Symbol>>>
) {
    LaunchedEffect(symbolManager.value) {
        val (style, symbolManager) = symbolManager.value ?: return@LaunchedEffect
        markers.collect { markers ->
            attachedMarkers.value.filter {
                it.first !in markers
            }.let { items ->
                items.forEach { (id, symbol) ->
                    style.removeImage(id.toString())
                    symbolManager.delete(symbol)
                }
                attachedMarkers.value = attachedMarkers.value.filter { it !in items }
            }
            val markers = markers.filter {
                it !in attachedMarkers.value.map { symbol -> symbol.first }
            }.map {
                async {
                    it to createMarkerBitmapWithBackground(
                        context = context,
                        imageUrl = it.avatar,
                        backgroundResId = when {
                            it.open -> R.drawable.livepinopen
                            else -> R.drawable.livepin
                        },
                        widthDp = when {
                            it.selected -> 76
                            else -> 62
                        },
                        heightDp = when {
                            it.selected -> 96
                            else -> 76
                        },
                    )
                }
            }.awaitAll()
            markers.forEach { (gym, bitmap) ->
                val bitmap = bitmap ?: return@forEach
                style.addImage(gym.toString(), bitmap)
                val symbol = symbolManager.create(
                    SymbolOptions()
                        .withLatLng(gym.latLng.toLatLng())
                        .withIconImage(gym.toString())
                        .withIconAnchor("bottom")
                )
                symbolManager.update(symbol)
                attachedMarkers.value += gym to symbol
            }
        }

    }
}