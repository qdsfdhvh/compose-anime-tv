package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvControllerKey
import com.seiko.compose.focuskit.TvLazyRow
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.compose.focuskit.refocus
import com.seiko.compose.focuskit.rememberRootTvFocusItem
import com.seiko.compose.focuskit.rememberTvFocusItem
import com.seiko.compose.focuskit.tvFocusTarget
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.SetSystemBarColor
import com.seiko.tv.anime.component.foundation.RoundIcon
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.util.extensions.clickableNoRipple

@Composable
fun HomeScene() {
  SetSystemBarColor()

  val list = remember {
    listOf(
      Icons.Filled.Home to "热门",
      Icons.Filled.Settings to "设置"
    )
  }

  val navigator = LocalAppNavigator.current
  val container = rememberRootTvFocusItem()

  Box(
    Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .background(MaterialTheme.colors.background)
  ) {
    TvLazyRow(
      container = container,
      modifier = Modifier.align(Alignment.Center)
    ) {
      itemsIndexed(list) { index, item ->
        val focusItem = rememberTvFocusItem(
          key = item,
          container = container,
          index = index
        )
        var isFocused by remember { mutableStateOf(false) }
        RoundIcon(
          image = item.first,
          name = item.second,
          isFocused = isFocused,
          modifier = Modifier
            .clickableNoRipple {
              navigator.push(Router.Feed)
            }
            .handleTvKey(TvControllerKey.Enter) {
              navigator.push(Router.Feed)
            }
            .onFocusChanged {
              isFocused = it.isFocused
            }
            .tvFocusTarget(focusItem)
        )
      }
    }
  }

  LaunchedEffect(list) {
    container.refocus()
  }
}
