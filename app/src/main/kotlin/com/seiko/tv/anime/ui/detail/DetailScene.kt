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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.component.ShowProgress
import com.seiko.tv.anime.component.foundation.TvEpisodeList
import com.seiko.tv.anime.component.foundation.TvMovieInfo
import com.seiko.tv.anime.component.foundation.TvTitleGroup
import com.seiko.tv.anime.model.AnimeDetail

@Composable
fun DetailScene(animeId: Int) {
  val viewModel = detailViewModel(animeId)
  val detail by viewModel.detail.collectAsState()

  if (detail === AnimeDetail.Empty) {
    ShowProgress()
    return
  }

  val focusRequesters = rememberFocusRequesters(3)
  val interactionSource = remember(detail) { MutableInteractionSource() }
  val focusIndex by interactionSource.collectFocusIndexAsState()

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
      interactionSource = interactionSource,
    ) {
      item {
        TvMovieInfo(
          title = detail.title,
          cover = detail.cover,
          releaseTime = detail.releaseTime,
          state = detail.state,
          tags = detail.tags,
          description = detail.description,
          modifier = Modifier.focusRequester(focusRequesters[0]),
        )
      }

      item {
        TvEpisodeList(
          title = "播放列表",
          list = detail.episodeList,
          modifier = Modifier.focusRequester(focusRequesters[1]),
        )
      }

      item {
        TvTitleGroup(
          title = "相关推荐",
          list = detail.relatedList,
          modifier = Modifier.focusRequester(focusRequesters[2]),
        )
      }
    }
  }

  LaunchedEffect(focusIndex) {
    focusRequesters.getOrNull(focusIndex)?.requestFocus()
  }
}
