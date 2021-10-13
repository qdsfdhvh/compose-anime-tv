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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.scrollToIndex
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup

@Composable
fun FeedAnimePage(tab: AnimeTab, modifier: Modifier = Modifier) {
  val viewModel = feedAnimeViewModel(tab)
  val animeList by viewModel.animeList.collectAsState()

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  var parentIsFocused by remember { mutableStateOf(false) }
  var parentHasFocused by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .onFocusChanged {
        parentHasFocused = it.hasFocus
        parentIsFocused = it.isFocused
      }
      .focusTarget(),
    state = listState,
  ) {
    itemsIndexed(animeList) { index, item ->
      val focusRequester = remember { FocusRequester() }

      TvTitleGroup(
        title = item.title,
        list = item.animes,
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) focusIndex = index
          }
          .focusOrder(focusRequester)
      )

      if (parentIsFocused && focusIndex == index) {
        SideEffect { focusRequester.requestFocus() }
      }
    }
  }

  if (parentHasFocused) {
    LaunchedEffect(focusIndex) {
      listState.scrollToIndex(focusIndex, ScrollBehaviour.Vertical)
    }
  }
}
