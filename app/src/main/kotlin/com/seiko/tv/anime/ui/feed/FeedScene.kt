package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvTabBar
import moe.tlaster.koin.compose.getViewModel
import moe.tlaster.precompose.navigation.NavController

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScene(navController: NavController) {
  SetSystemBarColor()

  val viewModel: FeedViewModel = getViewModel()
  val tabs by viewModel.tabs.collectAsState()

  if (tabs.isEmpty()) {
    LoadingState()
    return
  }

  var isTabFocused by rememberSaveable { mutableStateOf(true) }
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  val tabFocusRequester = remember { FocusRequester() }

  val pagerState = rememberPagerState()
  val pagerFocusRequesters = remember(tabs) { List(tabs.size) { FocusRequester() } }

  Surface(color = MaterialTheme.colors.background) {
    Column(Modifier.statusBarsPadding()) {
      TvTabBar(
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) isTabFocused = true
          }
          .focusOrder(tabFocusRequester),
        tabList = tabs,
        focusIndex = focusIndex,
        onFocusIndexChange = { index ->
          focusIndex = index
        }
      )

      HorizontalPager(
        count = tabs.size,
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) isTabFocused = false
          }
          .focusTarget(),
        state = pagerState,
      ) { index ->
        // ≈ dragEnabled = false
        // @see https://github.com/google/accompanist/issues/756#issuecomment-953610678
        Box(
          modifier = Modifier
            .pointerInput(Unit) {
              detectDragGestures { _, _ -> }
            }
        ) {
          FeedAnimePage(
            navController = navController,
            tab = tabs[index],
            modifier = Modifier
              .onFocusChanged {
                if (it.hasFocus) focusIndex = index
              }
              .focusOrder(pagerFocusRequesters[index])
          )
        }
      }
    }
  }

  LaunchedEffect(focusIndex) {
    pagerState.animateScrollToPage(focusIndex)
  }

  LaunchedEffect(isTabFocused) {
    if (isTabFocused) {
      tabFocusRequester.requestFocus()
    } else {
      pagerFocusRequesters[focusIndex].requestFocus()
    }
  }
}
