package com.seiko.compose.focuskit

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import com.seiko.compose.focuskit.internal.HorizontalScrollBehaviour
import com.seiko.compose.focuskit.internal.VerticalScrollBehaviour

interface ScrollBehaviour {
  suspend fun onScroll(state: LazyListState, focusIndex: Int)

  companion object {
    val Horizontal: ScrollBehaviour get() = HorizontalScrollBehaviour
    val Vertical: ScrollBehaviour get() = VerticalScrollBehaviour
  }
}

abstract class ItemScrollBehaviour : ScrollBehaviour {
  override suspend fun onScroll(state: LazyListState, focusIndex: Int) {
    val focusItem = state.layoutInfo.visibleItemsInfo.find { focusIndex == it.index }
    if (focusItem != null) onScroll(state, focusItem)
  }

  abstract suspend fun onScroll(state: LazyListState, focusItem: LazyListItemInfo)
}

suspend fun LazyListState.tweenAnimateScrollBy(value: Float) {
  stopScroll()
  animateScrollBy(value, tween(SCROLL_ANIMATION_DURATION, 0, LinearEasing))
}

private const val SCROLL_ANIMATION_DURATION = 150

suspend fun LazyListState.animateScrollToItem(focusIndex: Int, scrollBehaviour: ScrollBehaviour) {
  scrollBehaviour.onScroll(this, focusIndex)
}
