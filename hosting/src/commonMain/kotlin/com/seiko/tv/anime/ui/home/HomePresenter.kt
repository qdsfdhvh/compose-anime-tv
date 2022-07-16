package com.seiko.tv.anime.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun HomePresenter(): HomeState {
  val list = remember {
    listOf(
      HomeItem.Home,
      HomeItem.Favorite,
      HomeItem.Setting,
    )
  }
  return HomeState.Idle(list)
}

sealed interface HomeState {
  data class Idle(
    val items: List<HomeItem>,
  ) : HomeState
  object Loading : HomeState
}

sealed interface HomeItem {
  object Home : HomeItem
  object Favorite : HomeItem
  object Setting : HomeItem
}

val HomeItem.icon: ImageVector
  get() = when (this) {
    HomeItem.Home -> Icons.Filled.Home
    HomeItem.Favorite -> Icons.Filled.Favorite
    HomeItem.Setting -> Icons.Filled.Settings
  }

val HomeItem.name: String
  get() = when (this) {
    HomeItem.Home -> "热门"
    HomeItem.Favorite -> "收藏"
    HomeItem.Setting -> "设置"
  }
