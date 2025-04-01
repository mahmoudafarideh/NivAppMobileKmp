package ir.niv.app.ui.support.details

import m.a.compilot.common.RouteNavigation
import m.a.compilot.common.RouteType

@RouteNavigation(RouteType.Screen)
data class TicketDetailsRoute(
    val id: Long
) {
    companion object
}