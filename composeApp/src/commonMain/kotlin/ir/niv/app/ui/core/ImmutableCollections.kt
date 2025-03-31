package ir.niv.app.ui.core

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

inline fun <T, R> Iterable<T>.immutableMap(transform: (T) -> R): ImmutableList<R> {
    return this.map(transform).toImmutableList()
}