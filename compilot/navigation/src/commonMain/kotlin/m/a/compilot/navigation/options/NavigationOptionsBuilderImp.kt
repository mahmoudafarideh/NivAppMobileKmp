package m.a.compilot.navigation.options

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavOptionsBuilder
import m.a.compilot.navigation.controller.destinationId
import m.a.compilot.navigation.result.NavigationHandler
import m.a.compilot.navigation.result.NavigationResultImp


internal class NavigationOptionsBuilderImp : NavigationOptionsBuilder {
    private var shouldClearBackStack = false
    private var results = listOf<NavigationResultImp>()
    private var checkShouldNavigate = false
    private var safeNavigate = false
    private var forbiddenRoutes: List<String> = emptyList()

    override fun clearBackStack() {
        shouldClearBackStack = true
    }

    override fun setResult(key: String, result: NavigationHandler.() -> Unit) {
        results += NavigationResultImp(key).apply(result)
    }

    override fun safeNavigate() {
        safeNavigate = true
    }

    override fun checkShouldNavigate() {
        checkShouldNavigate = true
    }

    override fun checkNotInRoute(vararg route: String) {
        forbiddenRoutes += route.asList()
    }

    fun shouldNavigate(
        route: String,
        currentDestination: NavDestination?,
        currentDestinationId: Int?
    ): Boolean {
        return (!safeNavigate || currentDestination?.destinationId == currentDestinationId) &&
                (!checkShouldNavigate || currentDestination?.route != route) &&
                (forbiddenRoutes.isEmpty() || currentDestination?.route !in forbiddenRoutes)
    }

    fun NavOptionsBuilder.applyNavOptions() {
        if (shouldClearBackStack) {
            this.popUpToTop()
        }
    }

    fun NavBackStackEntry.applyResults() {
        results.forEach {
            savedStateHandle[it.key] = it.arguments
        }
    }

    private fun NavOptionsBuilder.popUpToTop() {
        popUpTo(0) {
            inclusive = true
        }
    }
}