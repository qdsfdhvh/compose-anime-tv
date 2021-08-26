package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.refocus
import com.seiko.compose.focuskit.rememberContainerTvFocusItem
import com.seiko.compose.focuskit.rememberRootTvFocusItem
import com.seiko.tv.anime.component.SetSystemBarColor
import com.seiko.tv.anime.component.foundation.TvTabBar
import com.seiko.tv.anime.component.foundation.TvTitleGroup

@Composable
fun FeedScene() {
  SetSystemBarColor()

  val viewModel: FeedViewModel = hiltViewModel()
  val tabList by viewModel.tabList.collectAsState()
  val animeList by viewModel.animeList.collectAsState()

  val container = rememberRootTvFocusItem()

  Scaffold {
    TvLazyColumn(
      container = container,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
      item {
        val tabContainer = rememberContainerTvFocusItem(
          key = Unit,
          container = container,
          index = 0
        )

        TvTabBar(
          tabList = tabList,
          parent = tabContainer
        )
      }
      itemsIndexed(animeList) { index, item ->
        val groupContainer = rememberContainerTvFocusItem(
          key = item,
          container = container,
          index = index + 1
        )

        TvTitleGroup(
          title = item.title,
          list = item.animes,
          parent = groupContainer
        )
      }
    }
  }

  LaunchedEffect(tabList) {
    container.refocus()
  }
}
