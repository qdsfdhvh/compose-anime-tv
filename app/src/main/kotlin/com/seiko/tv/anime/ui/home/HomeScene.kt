package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.seiko.tv.anime.ui.widget.foundation.TvTabBar
import com.seiko.tv.anime.ui.widget.foundation.TvTitleGroup

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
  val tabList by viewModel.tabList.collectAsState(emptyList())
  val animeList by viewModel.animeList.collectAsState(emptyList())

  Scaffold {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
      item { TvTabBar(tabList) }
      items(animeList) { item ->
        TvTitleGroup(
          title = item.title,
          list = item.animes
        )
      }
    }
  }
}