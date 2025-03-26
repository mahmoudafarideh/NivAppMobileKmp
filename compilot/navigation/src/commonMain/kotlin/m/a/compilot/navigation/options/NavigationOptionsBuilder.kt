package m.a.compilot.navigation.options

import m.a.compilot.navigation.result.NavigationHandler

/**
 * Interface representing a builder for navigation options.
 * This interface provides methods for configuring navigation behaviors and options.
 */
interface NavigationOptionsBuilder {

    /**
     * Clears the back stack of the navigation controller.
     */
    fun clearBackStack()

    /**
     * Sets a result to be returned to the previous destination.
     *
     * @param key The key associated with the result.
     * @param result The result to be set, defined by a [NavigationHandler] block.
     */
    fun setResult(key: String, result: NavigationHandler.() -> Unit = {})

    /**
     * Navigates to the specified destination only if it is called from current compose nav graph.
     */
    fun safeNavigate()

    /**
     * Checks if navigation should occur based on the current route.
     * If the navigation route equals to the current route it won't navigate.
     */
    fun checkShouldNavigate()

    /**
     * Checks if the current route is not within the specified navigation routes.
     *
     * @param route The [m.a.compilot.common.RouteNavigator] to check against.
     */
    fun checkNotInRoute(vararg route: String)
}
