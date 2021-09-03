package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.compose.focuskit.FocusIndexInteraction
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.onTvKeyEvent
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.ShowProgress
import com.seiko.tv.anime.ui.common.foundation.TvTabBar
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScene() {
  SetSystemBarColor()

  val viewModel: FeedViewModel = hiltViewModel()
  val tabs by viewModel.tabs.collectAsState()

  if (tabs.isEmpty()) {
    ShowProgress()
    return
  }

  val pagerState = rememberPagerState(pageCount = tabs.size)

  val focusRequesters = rememberFocusRequesters(1 + tabs.size)
  var focusIndex by rememberSaveable(stateSaver = Saver) { mutableStateOf(0) }

  val scope = rememberCoroutineScope()
  val tabInteractionSource = remember { MutableInteractionSource() }

  Surface(color = MaterialTheme.colors.background) {
    Column(
      modifier = Modifier.onTvKeyEvent {
        when (it) {
          TvKeyEvent.Up -> {
            if (focusIndex != 0) {
              focusIndex = 0
              true
            } else false
          }
          TvKeyEvent.Down -> {
            if (focusIndex == 0) {
              focusIndex = 1 + pagerState.currentPage
              true
            } else false
          }
          TvKeyEvent.Left -> {
            // if (pagerState.currentPage > 0) {
            //   scope.launch {
            //     val page = pagerState.currentPage - 1
            //     pagerState.animateScrollToPage(page)
            //     focusIndex = 1 + page
            //   }
            //   true
            // } else false
            true
          }
          TvKeyEvent.Right -> {
            // if (pagerState.currentPage < tabs.size - 1) {
            //   scope.launch {
            //     val page = pagerState.currentPage + 1
            //     pagerState.animateScrollToPage(page)
            //     focusIndex = 1 + page
            //   }
            //   true
            // } else false
            true
          }
          else -> false
        }
      }
    ) {

      TvTabBar(
        tabList = tabs,
        modifier = Modifier.focusRequester(focusRequesters[0]),
        interactionSource = tabInteractionSource
      )

      HorizontalPager(state = pagerState) { index ->
        FeedAnimePage(tabs[index], modifier = Modifier.focusRequester(focusRequesters[1 + index]))
      }
    }
  }

  LaunchedEffect(Unit) {
    tabInteractionSource.interactions
      .filter { focusIndex == 0 }
      .collect { interaction ->
        if (interaction is FocusIndexInteraction) {
          pagerState.animateScrollToPage(interaction.index)
        }
      }
  }

  // LaunchedEffect(Unit) {
  //   snapshotFlow { pagerState.currentPage }
  //     .filter { focusIndex != 0 }
  //     .collect { page ->
  //       tabInteractionSource.tryEmit(FocusIndexInteraction(page))
  //     }
  // }

  LaunchedEffect(focusIndex, tabs, pagerState) {
    withFrameNanos {}
    focusRequesters.getOrNull(focusIndex)?.requestFocus()
  }
}

@Composable
fun FeedAnimePage(tab: AnimeTab, modifier: Modifier = Modifier) {
  val viewModel = feedAnimeViewModel(tab)
  val animeList by viewModel.animeList.collectAsState()

  val focusRequesters = rememberFocusRequesters(animeList.size)
  val interactionSource = remember { MutableInteractionSource() }
  val focusIndex by interactionSource.collectFocusIndexAsState()
  var isParentFocused by remember { mutableStateOf(false) }

  TvLazyColumn(
    modifier = modifier
      .onFocusChanged { isParentFocused = it.hasFocus || it.isFocused }
      .fillMaxSize(),
    interactionSource = interactionSource,
  ) {
    itemsIndexed(animeList) { index, item ->
      TvTitleGroup(
        title = item.title,
        list = item.animes,
        modifier = Modifier.focusRequester(focusRequesters[index])
      )
    }
  }

  LaunchedEffect(focusIndex, animeList, isParentFocused) {
    withFrameNanos {}
    if (isParentFocused) {
      focusRequesters.getOrNull(focusIndex)?.requestFocus()
    }
  }
}

private val Saver = mapSaver(
  save = { mapOf("index" to it) },
  restore = { it["index"] as? Int ?: 0 }
)
