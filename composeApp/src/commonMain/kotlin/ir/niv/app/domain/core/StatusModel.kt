package ir.niv.app.domain.core

class StatusModel(
    val label: String,
    val state: State
) {
    enum class State {
        Success,
        Warning,
        Primary,
        Danger
    }
}