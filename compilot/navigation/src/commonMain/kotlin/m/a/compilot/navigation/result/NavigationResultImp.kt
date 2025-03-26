package m.a.compilot.navigation.result

import androidx.core.bundle.bundleOf

internal class NavigationResultImp(val key: String) : NavigationHandler {

    override fun setString(key: String, value: String) {
        _arguments += key to value
    }

    override fun setKey(key: String) {
        _arguments += key to ""
    }

    override fun setBoolean(key: String, value: Boolean) {
        _arguments += key to value
    }

    override fun setInt(key: String, value: Int) {
        _arguments += key to value
    }

    private val _arguments = mutableListOf<Pair<String, Any>>()
    val arguments get() = bundleOf(*_arguments.toTypedArray())
}