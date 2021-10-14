package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.onFocusDirection
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeedAnimePage(tab: AnimeTab, modifier: Modifier = Modifier) {
  val viewModel = feedAnimeViewModel(tab)
  val animeList by viewModel.animeList.collectAsState()

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  var parentIsFocused by remember { mutableStateOf(false) }
  var parentHasFocused by remember { mutableStateOf(false) }

  val focusRequesters = remember(animeList) { Array(animeList.size) { FocusRequester() } }

  val focusManager = LocalFocusManager.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .onFocusDirection {
        if (!parentHasFocused) return@onFocusDirection false
        when {
          focusIndex == 0 && it == FocusDirection.Up -> {
            focusManager.moveFocus(FocusDirection.Out)
          }
        }
        false
      }
      .onFocusChanged {
        parentHasFocused = it.hasFocus
        parentIsFocused = it.isFocused
      }
      .focusTarget(),
    state = listState,
  ) {
    itemsIndexed(animeList) { index, item ->
      TvTitleGroup(
        title = item.title,
        list = item.animes,
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) focusIndex = index
          }
          .focusOrder(focusRequesters[index]) {
            focusRequesters.getOrNull(index - 1)?.let { up = it }
            focusRequesters.getOrNull(index + 1)?.let { down = it }
          }
      )

      if (parentIsFocused && focusIndex == index) {
        SideEffect {
          focusRequesters[index].requestFocus()
        }
      }
    }
  }

  if (parentHasFocused) {
    LaunchedEffect(focusIndex) {
      listState.animateScrollToItem(focusIndex, ScrollBehaviour.Vertical)
    }
  }
}
