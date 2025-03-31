package ir.niv.app.ui.support.list

import ir.niv.app.domain.support.TicketCategory
import ir.niv.app.domain.support.UserTicket
import ir.niv.app.ui.core.ContinuousDeferredData
import ir.niv.app.ui.core.DeferredData
import ir.niv.app.ui.core.ReadyToFetch
import ir.niv.app.ui.core.ReadyToInitialFetch
import ir.niv.app.ui.core.StatusUiModel
import ir.niv.app.ui.core.toStatusUiModel
import kotlinx.collections.immutable.ImmutableList

data class TicketsUiModel(
    val tickets: ContinuousDeferredData<ImmutableList<TicketUiModel>> =
        ReadyToInitialFetch(1, 20),
    val categories: DeferredData<ImmutableList<TicketCategoryUiModel>> = ReadyToFetch,
)

data class TicketUiModel(
    val id: Long,
    val title: String,
    val status: StatusUiModel
)

data class TicketCategoryUiModel(
    val id: Long,
    val name: String
)

internal fun UserTicket.toTicketUiModel() = TicketUiModel(
    id = id,
    title = title,
    status = status.toStatusUiModel()
)

internal fun TicketCategory.toTicketCategoryUiModel() = TicketCategoryUiModel(
    id = id, name = name
)