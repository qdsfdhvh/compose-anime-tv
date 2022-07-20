package com.seiko.tv.anime.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.onFocusDirection
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.model.anime.AnimeGroup
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.ui.foundation.ErrorState
import com.seiko.tv.anime.ui.foundation.LoadingIndicator
import com.seiko.tv.anime.ui.foundation.TvTitleGroup
import io.github.aakira.napier.Napier
import moe.tlaster.precompose.rememberEvent
import moe.tlaster.precompose.rememberPresenter

@Composable
fun FeedAnimePage(
  tab: AnimeTab,
  onAnimeClick: (Anime) -> Unit,
  modifier: Modifier = Modifier
) {
  val (channel, event) = rememberEvent<FeedAnimeEvent>()
  val stateFlow = rememberPresenter { FeedAnimePresenter(event, tab) }
  when (val state = stateFlow.collectAsState().value) {
    FeedAnimeState.Loading -> {
      LoadingIndicator(
        modifier = modifier,
      )
    }

    is FeedAnimeState.Error -> {
      ErrorState(
        onRetry = {
          channel.trySend(FeedAnimeEvent.Retry)
        },
        message = state.message,
        modifier = modifier,
      )
    }

    is FeedAnimeState.Success -> {
      FeedAnimePage(
        animeList = state.list,
        onAnimeClick = onAnimeClick,
        modifier = modifier,
      )
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeedAnimePage(
  animeList: List<AnimeGroup>,
  onAnimeClick: (Anime) -> Unit,
  modifier: Modifier = Modifier
) {
  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  var parentIsFocused by remember { mutableStateOf(false) }
  var parentHasFocused by remember { mutableStateOf(false) }

  val focusRequesters = remember(animeList) { Array(animeList.size) { FocusRequester() } }

  val focusManager = LocalFocusManager.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .onFocusDirection {
        if (!parentHasFocused) return@onFocusDirection false
        when {
          focusIndex == 0 && it == FocusDirection.Up -> {
            focusManager.moveFocus(FocusDirection.Out)
          }
        }
        false
      }
      .onFocusChanged {
        parentHasFocused = it.hasFocus
        parentIsFocused = it.isFocused
        if (it.isFocused) {
          Napier.d(tag = "Feed") { "root has focus" }
        }
      }
      .focusTarget(),
    state = listState
  ) {
    itemsIndexed(animeList) { index, item ->
      TvTitleGroup(
        title = item.title,
        list = item.animes,
        onAnimeClick = onAnimeClick,
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) {
              focusIndex = index
              Napier.d(tag = "Feed") { "${item.title} has focus, index=$index" }
            }
          }
          .focusRequester(focusRequesters[index]).focusProperties {
            focusRequesters.getOrNull(index - 1)?.let { up = it }
            focusRequesters.getOrNull(index + 1)?.let { down = it }
          }
      )

      if (parentIsFocused && focusIndex == index) {
        SideEffect {
          focusRequesters[index].requestFocus()
        }
      }
    }
  }

  if (parentHasFocused) {
    LaunchedEffect(focusIndex) {
      listState.animateScrollToItem(focusIndex, ScrollBehaviour.Vertical)
    }
  }
}
