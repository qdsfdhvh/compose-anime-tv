package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvEpisodeList
import com.seiko.tv.anime.ui.common.foundation.TvTitleGroup
import com.seiko.tv.anime.ui.composer.navigation.Router

@Composable
fun DetailScene(uri: String) {
  val viewModel = detailViewModel(uri)
  val viewState by viewModel.viewState.collectAsState()

  if (viewState === DetailViewState.Empty) {
    LoadingState()
    return
  }

  val navController = LocalAppNavigator.current

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Surface(color = MaterialTheme.colors.background) {
    LazyColumn(
      modifier = Modifier
        .focusTarget()
        .fillMaxSize()
        .statusBarsPadding(),
      state = listState,
    ) {
      item {
        val focusRequester = remember { FocusRequester() }
        DetailAnimeInfo(
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 0 }
            .focusOrder(focusRequester),
          title = viewState.anime.title,
          cover = viewState.anime.cover,
          releaseTime = viewState.anime.releaseTime,
          state = viewState.anime.state,
          tags = viewState.anime.tags,
          types = viewState.anime.types,
          indexes = viewState.anime.indexes,
          description = viewState.anime.description,
          isFavorite = viewState.isFavorite,
          onFavoriteClick = {
            viewModel.send(DetailViewAction.ToggleFavorite)
          },
          onTagClick = { tag ->
            navController.push(Router.TagPage(tag.uri))
          }
        )

        if (focusIndex == 0) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }

      item {
        val focusRequester = remember { FocusRequester() }
        TvEpisodeList(
          title = "播放列表",
          list = viewState.anime.episodeList,
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 1 }
            .focusOrder(focusRequester),
        )

        if (focusIndex == 1) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }

      item {
        val focusRequester = remember { FocusRequester() }
        TvTitleGroup(
          title = "相关推荐",
          list = viewState.anime.relatedList,
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 2 }
            .focusOrder(focusRequester),
        )

        if (focusIndex == 2) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }
    }
  }

  LaunchedEffect(focusIndex) {
    listState.animateScrollToItem(focusIndex, ScrollBehaviour.Vertical)
  }
}
