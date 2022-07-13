package com.seiko.tv.anime.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.paging.compose.LazyPagingItems
import com.seiko.compose.focuskit.ItemScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesterManager
import com.seiko.compose.focuskit.tweenAnimateScrollBy
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.foundation.GroupItem
import com.seiko.tv.anime.ui.common.foundation.ScreenState
import com.seiko.tv.anime.ui.common.foundation.itemsGridIndexed
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberPresenter

private const val FavoriteColumnNum = 6

@Composable
fun FavoriteScene(navigator: Navigator) {
  val stateFlow = rememberPresenter { FavoritePresenter() }
  when (val state = stateFlow.collectAsState().value) {
    is FavoriteState.Success -> {
      FavoriteScene(
        pagingItems = state.pagingItems,
        onAnimeClick = { anime ->
          navigator.navigate(Router.Detail(anime.uri))
        }
      )
    }
  }
}

@Composable
fun FavoriteScene(
  pagingItems: LazyPagingItems<Anime>,
  onAnimeClick: (Anime) -> Unit,
) {
  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }
  val focusRequesters = rememberFocusRequesterManager()

  Surface(
    color = MaterialTheme.colorScheme.background,
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
            style = MaterialTheme.typography.titleLarge
          )
        }
      }

      itemsGridIndexed(pagingItems, FavoriteColumnNum) { index, item ->
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
              onAnimeClick.invoke(item)
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

      ScreenState(pagingItems)
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
