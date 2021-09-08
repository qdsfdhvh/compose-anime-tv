package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.ui.common.ShowProgress
import com.seiko.tv.anime.ui.common.foundation.TvEpisodeList
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup

@Composable
fun DetailScene(uri: String) {
  val viewModel = detailViewModel(uri)
  val viewState by viewModel.viewState.collectAsState()

  if (viewState === DetailViewState.Empty) {
    ShowProgress()
    return
  }

  val focusRequesters = rememberFocusRequesters(3)
  val interactionSource = remember(viewState) { MutableInteractionSource() }
  val focusIndex by interactionSource.collectFocusIndexAsState()

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
      interactionSource = interactionSource,
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
