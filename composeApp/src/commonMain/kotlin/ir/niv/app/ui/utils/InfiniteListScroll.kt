package ir.niv.app.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun InfiniteListScrollDown(
    lazyListState: LazyListState,
    onReachEnd: () -> Unit
) {
    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached) {
            onReachEnd()
        }
    }
}

