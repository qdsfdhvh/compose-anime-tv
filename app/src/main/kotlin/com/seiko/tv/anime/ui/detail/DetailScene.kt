package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.rememberContainerTvFocusItem
import com.seiko.tv.anime.component.foundation.TvEpisodeList
import com.seiko.tv.anime.component.foundation.TvMovieInfo
import com.seiko.tv.anime.component.foundation.TvTitleGroup
import com.seiko.tv.anime.di.assisted.assistedViewModel

@Composable
fun DetailScene(id: Int) {
  val viewModel = assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(id)
  }

  val container = rememberContainerTvFocusItem()

  val info by viewModel.info.collectAsState()

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      container = container,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
    ) {
      item {
        TvMovieInfo(
          title = info.title,
          cover = info.cover,
          releaseTime = info.releaseTime,
          state = info.state,
          tags = info.tags,
          description = info.description,
        )
      }

      item {
        val episodeContainer = rememberContainerTvFocusItem(
          key = Unit,
          container = container,
          index = 0
        )

        TvEpisodeList(
          title = "播放列表",
          list = info.episodeList,
          parent = episodeContainer,
        )

        LaunchedEffect(info) {
          episodeContainer.focusIndex = 0
        }
      }

      item {
        val relatedContainer = rememberContainerTvFocusItem(
          key = Unit,
          container = container,
          index = 1
        )

        TvTitleGroup(
          parent = relatedContainer,
          title = "相关推荐",
          list = info.relatedList
        )
      }
    }
  }
}