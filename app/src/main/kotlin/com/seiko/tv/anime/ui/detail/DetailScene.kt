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
import com.seiko.compose.focuskit.refocus
import com.seiko.compose.focuskit.rememberContainerTvFocusItem
import com.seiko.compose.focuskit.rememberRootTvFocusItem
import com.seiko.tv.anime.component.foundation.TvEpisodeList
import com.seiko.tv.anime.component.foundation.TvMovieInfo
import com.seiko.tv.anime.component.foundation.TvTitleGroup

@Composable
fun DetailScene(animeId: Int) {
  val viewModel = detailViewModel(animeId)
  val detail by viewModel.detail.collectAsState()

  val container = rememberRootTvFocusItem()

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      container = container,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
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
