package com.seiko.tv.anime.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel : ViewModel() {
  val list: StateFlow<List<HomeItem>> = flow {
    emit(
      listOf(
        HomeItem.Home,
        HomeItem.Favorite,
        HomeItem.Setting
      )
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

sealed class HomeItem(
  val icon: ImageVector,
  val name: String,
) {
  object Home : HomeItem(Icons.Filled.Home, "热门")
  object Favorite : HomeItem(Icons.Filled.Favorite, "收藏")
  object Setting : HomeItem(Icons.Filled.Settings, "设置")
}
