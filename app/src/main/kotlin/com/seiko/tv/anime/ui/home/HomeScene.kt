package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.seiko.compose.focuskit.*
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.foundation.TvTabBar
import com.seiko.tv.anime.component.foundation.TvTitleGroup

@Composable
fun HomeScene() {
  // 配置状态栏颜色
  val systemUiController = rememberSystemUiController()
  val useDarkIcons = MaterialTheme.colors.isLight
  SideEffect {
    systemUiController.setSystemBarsColor(
      color = Color.Transparent,
      darkIcons = useDarkIcons
    )
  }

  val viewModel: HomeViewModel = hiltViewModel()
  val tabList by viewModel.tabList.collectAsState()
  val animeList by viewModel.animeList.collectAsState()

  val container = rememberRootTvFocusItem()
  val navController = LocalAppNavigator.current

  Scaffold {
    TvLazyColumn(
      container = container,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .handleTvKey(TvControllerKey.Back) {
          navController.pop()
        }
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