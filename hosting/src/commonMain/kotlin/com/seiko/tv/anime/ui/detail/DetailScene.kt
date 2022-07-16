package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.model.anime.AnimeDetail
import com.seiko.tv.anime.model.anime.AnimeEpisode
import com.seiko.tv.anime.model.anime.AnimeTag
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.foundation.LoadingIndicator
import com.seiko.tv.anime.ui.foundation.TvEpisodeList
import com.seiko.tv.anime.ui.foundation.TvTitleGroup
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberEvent
import moe.tlaster.precompose.rememberPresenter
import com.seiko.tv.anime.util.statusBarsPadding

@Composable
fun DetailScene(
  navigator: Navigator,
  uri: String
) {
  val (channel, flow) = rememberEvent<DetailEvent>()
  val stateFlow = rememberPresenter { DetailPresenter(flow, uri) }
  when (val state = stateFlow.collectAsState().value) {
    DetailState.Loading -> LoadingIndicator()
    is DetailState.Success -> {
      DetailScene(
        detail = state.detail,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
          channel.trySend(DetailEvent.ToggleFavorite)
        },
        onTagClick = { tag ->
          navigator.navigate(Router.TagPage(tag.uri))
        },
        onEpisodeClick = { episode ->
          navigator.navigate(Router.Player(episode.uri))
        },
        onAnimeClick = { anime ->
          navigator.navigate(Router.Detail(anime.uri))
        },
      )
    }
  }
}

@Composable
fun DetailScene(
  detail: AnimeDetail,
  isFavorite: Boolean,
  onFavoriteClick: () -> Unit,
  onTagClick: (AnimeTag) -> Unit,
  onEpisodeClick: (AnimeEpisode) -> Unit,
  onAnimeClick: (Anime) -> Unit,
) {
  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Surface(color = MaterialTheme.colorScheme.background) {
    LazyColumn(
      modifier = Modifier
        .focusTarget()
        .fillMaxSize()
        .statusBarsPadding(),
      state = listState
    ) {
      item {
        val focusRequester = remember { FocusRequester() }
        DetailAnimeInfo(
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 0 }
            .focusRequester(focusRequester),
          title = detail.title,
          cover = detail.cover,
          releaseTime = detail.releaseTime,
          state = detail.state,
          tags = detail.tags,
          types = detail.types,
          indexes = detail.indexes,
          description = detail.description,
          isFavorite = isFavorite,
          onFavoriteClick = onFavoriteClick,
          onTagClick = onTagClick,
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
          list = detail.episodeList,
          onEpisodeClick = onEpisodeClick,
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 1 }
            .focusRequester(focusRequester)
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
          list = detail.relatedList,
          onAnimeClick = onAnimeClick,
          modifier = Modifier
            .onFocusChanged { if (it.isFocused) focusIndex = 2 }
            .focusRequester(focusRequester)
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
