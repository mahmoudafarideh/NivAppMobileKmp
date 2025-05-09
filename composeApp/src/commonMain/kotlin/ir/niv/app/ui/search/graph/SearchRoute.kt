package ir.niv.app.ui.search.graph

import ir.niv.app.ui.search.model.GymMapUiModel
import m.a.compilot.common.RouteNavigation
import m.a.compilot.common.RouteType

@RouteNavigation(RouteType.Screen)
data object SearchRoute

@RouteNavigation(RouteType.Screen)
data object MapSearchRoute

@RouteNavigation(RouteType.Dialog)
data class MapGymDetails(
    val gym: GymMapUiModel
) {
    companion object
}