package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesterManager
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.RoundIcon
import moe.tlaster.koin.compose.getViewModel
import moe.tlaster.precompose.navigation.NavController

@Composable
fun HomeScene(
  navController: NavController,
) {
  SetSystemBarColor()

  val viewModel: HomeViewModel = getViewModel()
  val list by viewModel.list.collectAsState()

  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background)
  ) {
    LazyRow(Modifier.align(Alignment.Center)) {
      itemsIndexed(list) { index, item ->
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        RoundIcon(
          image = item.icon,
          name = item.name,
          isFocused = isFocused,
          modifier = Modifier
            .focusClick {
              focusRequester.requestFocus()
              when (item) {
                HomeItem.Home -> navController.navigate(Router.Feed.route)
                HomeItem.Favorite -> navController.navigate(Router.Favorite.route)
                else -> Unit
              }
            }
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .focusOrder(focusRequester)
            .focusTarget()
        )

        if (focusIndex == index) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }
    }
  }
}
