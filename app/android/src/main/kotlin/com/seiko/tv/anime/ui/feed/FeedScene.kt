package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvTabBar
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberPresenter

@Composable
fun FeedScene(navigator: Navigator) {
  SetSystemBarColor()

  val stateFlow = rememberPresenter { FeedPresenter() }
  when (val state = stateFlow.collectAsState().value) {
    FeedState.Loading -> LoadingState()
    is FeedState.Success -> {
      FeedScene(
        tabs = state.list,
        onAnimeClick = { anime ->
          navigator.navigate(Router.Detail(anime.uri))
        }
      )
    }
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScene(
  tabs: List<AnimeTab>,
  onAnimeClick: (Anime) -> Unit,
) {
  var isTabFocused by rememberSaveable { mutableStateOf(true) }
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  val tabFocusRequester = remember { FocusRequester() }

  val pagerState = rememberPagerState()
  val pagerFocusRequesters = remember(tabs) { List(tabs.size) { FocusRequester() } }

  Surface(color = MaterialTheme.colorScheme.background) {
    Column(Modifier.statusBarsPadding()) {
      TvTabBar(
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) isTabFocused = true
          }
          .focusRequester(tabFocusRequester),
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
        state = pagerState
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
            tab = tabs[index],
            onAnimeClick = onAnimeClick,
            modifier = Modifier
              .onFocusChanged {
                if (it.hasFocus) focusIndex = index
              }
              .focusRequester(pagerFocusRequesters[index])
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
