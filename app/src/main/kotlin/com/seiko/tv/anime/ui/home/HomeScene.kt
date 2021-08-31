package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.SetSystemBarColor
import com.seiko.tv.anime.component.foundation.RoundIcon
import com.seiko.tv.anime.navigation.Router

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

  val focusRequesters = rememberFocusRequesters(list)

  Box(
    Modifier
      .fillMaxSize()
      .statusBarsPadding()
      .background(MaterialTheme.colors.background)
  ) {
    LazyRow(
      modifier = Modifier.align(Alignment.Center),
    ) {
      itemsIndexed(list) { index, item ->
        val itemInteractionSource = remember { MutableInteractionSource() }
        RoundIcon(
          image = item.first,
          name = item.second,
          isFocused = itemInteractionSource.collectIsFocusedAsState().value,
          modifier = Modifier
            .focusClick {
              if (item.second == "热门") {
                navigator.push(Router.Feed)
              }
            }
            .focusRequester(focusRequesters[index])
            .focusable(interactionSource = itemInteractionSource)
        )
      }
    }
  }

  LaunchedEffect(list) {
    focusRequesters[0].requestFocus()
  }
}
