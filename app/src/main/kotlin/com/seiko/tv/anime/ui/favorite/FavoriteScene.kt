package com.seiko.tv.anime.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.compose.focuskit.ItemScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.tweenAnimateScrollBy
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.ui.common.foundation.GroupItem
import com.seiko.tv.anime.ui.common.foundation.LazyGridFor
import com.seiko.tv.anime.ui.composer.navigation.Router
import org.koin.androidx.compose.getViewModel

private const val FavoriteColumnNum = 5

@Composable
fun FavoriteScene() {
  val viewModel: FavoriteViewModel = getViewModel()
  val list = viewModel.favorites.collectAsLazyPagingItems()

  val navigator = LocalAppNavigator.current

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Surface(
    color = MaterialTheme.colors.background,
    modifier = Modifier.fillMaxSize(),
  ) {
    LazyGridFor(
      pagingItems = list,
      nColumns = FavoriteColumnNum,
      title = "收藏夹",
      state = listState
    ) { anime, index ->
      val focusRequester = remember { FocusRequester() }
      var isFocused by remember { mutableStateOf(false) }
      GroupItem(
        item = anime,
        isFocused = isFocused,
        modifier = Modifier
          .onFocusChanged {
            isFocused = it.isFocused
            if (isFocused) focusIndex = index
          }
          .focusClick {
            focusRequester.requestFocus()
            navigator.push(Router.Detail(anime.uri))
          }
          .focusOrder(focusRequester)
          .focusTarget()
      )

      if (focusIndex == index) {
        SideEffect {
          focusRequester.requestFocus()
        }
      }
    }
  }

  LaunchedEffect(focusIndex) {
    val moveIndex = if (focusIndex < FavoriteColumnNum) 0 else focusIndex / FavoriteColumnNum + 1
    listState.animateScrollToItem(moveIndex, FavoriteVerticalScroll)
  }
}

private object FavoriteVerticalScroll : ItemScrollBehaviour() {
  override suspend fun onScroll(
    state: LazyListState,
    focusItem: LazyListItemInfo
  ) {
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

  override suspend fun onScroll(state: LazyListState, focusIndex: Int) {
    var targetItem: LazyListItemInfo = state.layoutInfo.visibleItemsInfo.firstOrNull() ?: return
    if (targetItem.index <= focusIndex) {
      targetItem = state.layoutInfo.visibleItemsInfo.find { focusIndex == it.index } ?: return
      onScroll(state, targetItem)
    } else {
      var targetIndex = targetItem.index
      while (targetIndex >= focusIndex) {
        onScroll(state, targetItem)
        if (targetIndex == focusIndex) return

        targetItem = state.layoutInfo.visibleItemsInfo.firstOrNull() ?: return
        targetIndex = if (targetItem.index < focusIndex) focusIndex else targetItem.index
      }
    }
  }
}
