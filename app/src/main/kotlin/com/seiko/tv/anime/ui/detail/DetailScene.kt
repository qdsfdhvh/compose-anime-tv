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
import com.seiko.compose.focuskit.*
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.foundation.TvEpisodeList
import com.seiko.tv.anime.component.foundation.TvMovieInfo
import com.seiko.tv.anime.component.foundation.TvTitleGroup
import com.seiko.tv.anime.di.assisted.assistedViewModel

@Composable
fun DetailScene(id: Int) {
  val viewModel = assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(id)
  }

  val container = rememberRootTvFocusItem()

  val detail by viewModel.detail.collectAsState()
  val navController = LocalAppNavigator.current

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      container = container,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .handleTvKey(TvControllerKey.Back) {
          navController.pop()
        },
    ) {
      item {
        TvMovieInfo(
          title = detail.title,
          cover = detail.cover,
          releaseTime = detail.releaseTime,
          state = detail.state,
          tags = detail.tags,
          description = detail.description,
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
          list = detail.episodeList,
          parent = episodeContainer,
        )

        LaunchedEffect(detail) {
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
          list = detail.relatedList
        )
      }
    }
  }

  LaunchedEffect(detail) {
    container.refocus()
  }
}