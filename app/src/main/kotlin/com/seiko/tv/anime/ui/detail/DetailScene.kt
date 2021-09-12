package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.focusScrollVertical
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvEpisodeList
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup

@Composable
fun DetailScene(uri: String) {
  val viewModel = detailViewModel(uri)
  val viewState by viewModel.viewState.collectAsState()

  if (viewState === DetailViewState.Empty) {
    LoadingState()
    return
  }

  val focusRequesters = rememberFocusRequesters(3)
  val listState = rememberLazyListState()
  val focusIndex by listState.interactionSource.collectFocusIndexAsState()

  Surface(color = MaterialTheme.colors.background) {
    LazyColumn(
      modifier = Modifier
        .focusScrollVertical(listState)
        .focusable()
        .fillMaxSize()
        .statusBarsPadding(),
      state = listState,
    ) {
      item {
        DetailAnimeInfo(
          title = viewState.anime.title,
          cover = viewState.anime.cover,
          releaseTime = viewState.anime.releaseTime,
          state = viewState.anime.state,
          tags = viewState.anime.tags,
          description = viewState.anime.description,
          isFavorite = viewState.isFavorite,
          modifier = Modifier.focusRequester(focusRequesters[0]),
          onFavoriteClick = {
            viewModel.send(DetailViewAction.ToggleFavorite)
          }
        )
      }

      item {
        TvEpisodeList(
          title = "播放列表",
          list = viewState.anime.episodeList,
          modifier = Modifier.focusRequester(focusRequesters[1]),
        )
      }

      item {
        TvTitleGroup(
          title = "相关推荐",
          list = viewState.anime.relatedList,
          modifier = Modifier.focusRequester(focusRequesters[2]),
        )
      }
    }
  }

  LaunchedEffect(focusIndex) {
    withFrameNanos {}
    focusRequesters.getOrNull(focusIndex)?.requestFocus()
  }
}
