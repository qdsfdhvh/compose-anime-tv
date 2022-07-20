package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.widget.ErrorState
import com.seiko.tv.anime.ui.widget.LoadingIndicator
import com.seiko.tv.anime.ui.widget.TvTabBar
import com.seiko.tv.anime.util.statusBarsPadding
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberEvent
import moe.tlaster.precompose.rememberPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScene(navigator: Navigator) {
  val (channel, event) = rememberEvent<FeedEvent>()
  val stateFlow = rememberPresenter { FeedPresenter(event) }
  Scaffold { innerPadding ->
    when (val state = stateFlow.collectAsState().value) {
      FeedState.Loading -> {
        LoadingIndicator(
          modifier = Modifier.padding(innerPadding),
        )
      }

      is FeedState.Error -> {
        ErrorState(
          onRetry = {
            channel.trySend(FeedEvent.Retry)
          },
          message = state.message,
        )
      }

      is FeedState.Success -> {
        FeedScene(
          tabs = state.list,
          onAnimeClick = { anime ->
            navigator.navigate(Router.Detail(anime.uri))
          },
          modifier = Modifier.padding(innerPadding),
        )
      }
    }
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScene(
  tabs: List<AnimeTab>,
  onAnimeClick: (Anime) -> Unit,
  modifier: Modifier = Modifier,
) {
  var isTabFocused by rememberSaveable { mutableStateOf(true) }
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  val tabFocusRequester = remember { FocusRequester() }

  val pagerState = rememberPagerState()
  val pagerFocusRequesters = remember(tabs) { List(tabs.size) { FocusRequester() } }

  Column(modifier.statusBarsPadding()) {
    TvTabBar(
      modifier = Modifier
        .onFocusChanged {
          if (it.isFocused) isTabFocused = true
        }
        .focusProperties {
          down = pagerFocusRequesters[focusIndex]
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
      state = pagerState,
    ) { index ->
      FeedAnimePage(
        tab = tabs[index],
        onAnimeClick = onAnimeClick,
        modifier = Modifier
          .onFocusChanged {
            if (it.hasFocus) focusIndex = index
          }
          .focusProperties {
            left = pagerFocusRequesters.getOrNull(index - 1) ?: FocusRequester.Default
            right = pagerFocusRequesters.getOrNull(index + 1) ?: FocusRequester.Default
            up = tabFocusRequester
          }
          .focusRequester(pagerFocusRequesters[index])
      )
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
