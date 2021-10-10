package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvTabBar

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScene() {
  SetSystemBarColor()

  val viewModel: FeedViewModel = hiltViewModel()
  val tabs by viewModel.tabs.collectAsState()

  if (tabs.isEmpty()) {
    LoadingState()
    return
  }

  var isTabFocused by rememberSaveable(stateSaver = autoSaver()) { mutableStateOf(true) }
  var focusIndex by rememberSaveable(stateSaver = autoSaver()) { mutableStateOf(0) }

  val tabFocusRequester = remember { FocusRequester() }

  val pagerState = rememberPagerState(pageCount = tabs.size)
  val pagerFocusRequesters = remember(tabs) { List(tabs.size) { FocusRequester() } }

  Surface(color = MaterialTheme.colors.background) {
    Column(Modifier.statusBarsPadding()) {
      TvTabBar(
        modifier = Modifier
          .onFocusChanged { if (it.isFocused) isTabFocused = true }
          .focusOrder(tabFocusRequester),
        tabList = tabs,
        focusIndex = focusIndex,
        onFocusIndexChange = { index ->
          focusIndex = index
        }
      )

      HorizontalPager(
        modifier = Modifier
          .onFocusChanged { if (it.isFocused) isTabFocused = false }
          .focusTarget(),
        state = pagerState,
        dragEnabled = false
      ) { index ->
        FeedAnimePage(
          tabs[index],
          modifier = Modifier
            .onFocusChanged { if (it.hasFocus) focusIndex = index }
            .focusOrder(pagerFocusRequesters[index])
        )
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
