package com.seiko.tv.anime.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.compose.focuskit.ItemScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesterManager
import com.seiko.compose.focuskit.tweenAnimateScrollBy
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.foundation.GroupItem
import com.seiko.tv.anime.ui.common.foundation.itemsGridIndexed
import com.seiko.tv.anime.ui.common.foundation.screenState
import moe.tlaster.koin.compose.getViewModel
import moe.tlaster.precompose.navigation.NavController

private const val FavoriteColumnNum = 6

@Composable
fun FavoriteScene(navController: NavController) {
  val viewModel: FavoriteViewModel = getViewModel()
  val list = viewModel.favorites.collectAsLazyPagingItems()

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }
  val focusRequesters = rememberFocusRequesterManager()

  Surface(
    color = MaterialTheme.colors.background,
    modifier = Modifier.fillMaxSize()
  ) {
    LazyColumn(
      state = listState
    ) {
      item {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "收藏夹",
            style = MaterialTheme.typography.h3
          )
        }
      }

      itemsGridIndexed(list, FavoriteColumnNum) { index, item ->
        var isFocused by remember { mutableStateOf(false) }
        GroupItem(
          item = item,
          isFocused = isFocused,
          modifier = Modifier
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .focusClick {
              focusRequesters[index].requestFocus()

              navController.navigate(Router.Detail(item.uri))
            }
            .focusRequester(focusRequesters[index]).focusProperties {
              up = focusRequesters.getOrDefault(index - FavoriteColumnNum)
              down = focusRequesters.getOrDefault(index + FavoriteColumnNum)
              left = focusRequesters.getOrDefault(index - 1)
              right = focusRequesters.getOrDefault(index + 1)
            }
            .focusTarget()
        )

        if (focusIndex == index) {
          LaunchedEffect(focusIndex) {
            focusRequesters[focusIndex].requestFocus()
          }
        }
      }

      screenState(list)
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
