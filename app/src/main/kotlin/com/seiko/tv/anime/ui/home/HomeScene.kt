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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.RoundIcon
import com.seiko.tv.anime.ui.composer.navigation.Router

@Composable
fun HomeScene() {
  SetSystemBarColor()

  val navigator = LocalAppNavigator.current

  val viewModel: HomeViewModel = hiltViewModel()
  val list by viewModel.list.collectAsState()

  val focusRequesters = rememberFocusRequesters(list)
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background)
  ) {
    LazyRow(
      modifier = Modifier
        .align(Alignment.Center),
    ) {
      itemsIndexed(list) { index, item ->
        val itemInteractionSource = remember { MutableInteractionSource() }
        RoundIcon(
          image = item.icon,
          name = item.name,
          isFocused = itemInteractionSource.collectIsFocusedAsState().value,
          modifier = Modifier
            .focusClick {
              when (item) {
                HomeItem.Home -> navigator.push(Router.Feed)
                HomeItem.Favorite -> navigator.push(Router.Favorite)
                else -> {}
              }
            }
            .focusRequester(focusRequesters[index])
            .focusable(interactionSource = itemInteractionSource)
        )
      }
    }
  }

  LaunchedEffect(list) {
    withFrameNanos {}
    focusRequesters[0].requestFocus()
  }
}
