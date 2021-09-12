package com.seiko.tv.anime.ui.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.focusScroll
import com.seiko.compose.focuskit.startScrollNormal
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.ui.common.foundation.GroupItem
import com.seiko.tv.anime.ui.common.foundation.LazyGridFor
import com.seiko.tv.anime.ui.composer.navigation.Router

private const val FavoriteColumnNum = 5

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScene() {
  val viewModel: FavoriteViewModel = hiltViewModel()
  val list = viewModel.favorites.collectAsLazyPagingItems()

  val navigator = LocalAppNavigator.current

  val listState = rememberLazyListState()

  var focusIndex by rememberSaveable(stateSaver = indexSaver) {
    mutableStateOf(0)
  }

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
      val interactionSource = remember { MutableInteractionSource() }
      val moveIndex = remember { if (index < FavoriteColumnNum) 0 else index / FavoriteColumnNum + 1 }
      val focusRequester = remember { FocusRequester() }

      GroupItem(
        item = anime,
        isFocused = interactionSource.collectIsFocusedAsState().value,
        modifier = Modifier
          .onFocusChanged { if (it.isFocused) focusIndex = index }
          .focusClick { navigator.push(Router.Detail(anime.uri)) }
          .focusScroll(listState, moveIndex, FavoriteVerticalScroll)
          .focusRequester(focusRequester)
          .focusable(interactionSource = interactionSource)
      )

      if (focusIndex == index) {
        SideEffect {
          focusRequester.requestFocus()
        }
      }
    }
  }
}

private object FavoriteVerticalScroll : ScrollBehaviour {
  override suspend fun onScroll(
    state: LazyListState,
    focusItem: LazyListItemInfo,
    density: Density
  ) {
    val viewStart = state.layoutInfo.viewportStartOffset
    val viewEnd = state.layoutInfo.viewportEndOffset
    val viewSize = viewEnd - viewStart

    val itemStart = focusItem.offset
    val itemEnd = focusItem.offset + focusItem.size

    val offSect = density.run { 20.dp.roundToPx() }

    val value = when {
      itemStart < viewStart -> itemStart.toFloat() - offSect
      itemEnd > viewStart + viewSize -> (itemEnd - viewSize - viewStart).toFloat() + offSect
      else -> return
    }
    state.startScrollNormal(value)
  }

  // TODO wait to clean up code
  override suspend fun onScroll(state: LazyListState, index: Int, density: Density) {
    var targetItem: LazyListItemInfo? = state.layoutInfo.visibleItemsInfo.firstOrNull()
    if (targetItem != null) {
      onScroll(state, targetItem, density)
    }

    while (targetItem != null && targetItem.index != 0) {
      val findItem = state.layoutInfo.visibleItemsInfo.find { it.index == 0 }
      targetItem = findItem ?: state.layoutInfo.visibleItemsInfo.firstOrNull()

      if (targetItem != null) {
        onScroll(state, targetItem, density)
      }
    }
  }
}

private val indexSaver = mapSaver(
  save = { mapOf("index" to it) },
  restore = { it["index"] as? Int ?: 0 }
)
