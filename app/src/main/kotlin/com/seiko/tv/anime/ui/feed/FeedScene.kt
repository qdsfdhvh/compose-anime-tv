package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.component.SetSystemBarColor
import com.seiko.tv.anime.component.ShowProgress
import com.seiko.tv.anime.component.foundation.TvTabBar
import com.seiko.tv.anime.component.foundation.TvTitleGroup

@Composable
fun FeedScene() {
  SetSystemBarColor()

  val viewModel: FeedViewModel = hiltViewModel()
  val tabList by viewModel.tabList.collectAsState()
  val animeList by viewModel.animeList.collectAsState()

  if (animeList.isEmpty()) {
    ShowProgress()
    return
  }

  val size = remember(animeList) { 1 + animeList.size }
  val focusRequesters = rememberFocusRequesters(size)
  val interactionSource = remember { MutableInteractionSource() }
  val focusIndex by interactionSource.collectFocusIndexAsState()

  Scaffold {
    TvLazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
      interactionSource = interactionSource,
    ) {
      item {
        TvTabBar(
          tabList = tabList,
          modifier = Modifier.focusRequester(focusRequesters[0])
        )
      }
      itemsIndexed(animeList) { index, item ->
        TvTitleGroup(
          title = item.title,
          list = item.animes,
          modifier = Modifier.focusRequester(focusRequesters[1 + index])
        )
      }
    }
  }

  LaunchedEffect(focusIndex, animeList) {
    focusRequesters.getOrNull(focusIndex)?.requestFocus()
  }
}
