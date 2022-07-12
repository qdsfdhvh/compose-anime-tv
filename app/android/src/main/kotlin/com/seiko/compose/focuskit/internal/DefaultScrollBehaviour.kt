package com.seiko.compose.focuskit.internal

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import com.seiko.compose.focuskit.ItemScrollBehaviour
import com.seiko.compose.focuskit.tweenAnimateScrollBy

internal object HorizontalScrollBehaviour : ItemScrollBehaviour() {
  override suspend fun onScroll(state: LazyListState, focusItem: LazyListItemInfo) {
    val viewStart = state.layoutInfo.viewportStartOffset
    val viewEnd = state.layoutInfo.viewportEndOffset
    val viewSize = viewEnd - viewStart

    val itemStart = focusItem.offset
    val itemEnd = focusItem.offset + focusItem.size
    val itemSize = focusItem.size

    val leftLine = viewStart + viewSize * 0.3
    val rightLine = viewStart + viewSize * 0.7

    val value = when {
      itemStart > rightLine -> itemSize.toFloat()
      itemEnd < leftLine -> (-itemSize).toFloat()
      else -> return
    }
    state.tweenAnimateScrollBy(value)
  }
}

internal object VerticalScrollBehaviour : ItemScrollBehaviour() {
  override suspend fun onScroll(state: LazyListState, focusItem: LazyListItemInfo) {
    val viewStart = state.layoutInfo.viewportStartOffset
    val viewEnd = state.layoutInfo.viewportEndOffset
    val viewSize = viewEnd - viewStart

    val itemStart = focusItem.offset
    val itemEnd = focusItem.offset + focusItem.size

    val offSect = 80

    val value = when {
      itemStart < viewStart -> itemStart.toFloat() - offSect
      itemEnd > viewStart + viewSize -> (itemEnd - viewSize - viewStart).toFloat() + offSect
      else -> return
    }
    state.tweenAnimateScrollBy(value)
  }
}
