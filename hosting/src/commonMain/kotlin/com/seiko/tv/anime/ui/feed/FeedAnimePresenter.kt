package com.seiko.tv.anime.ui.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.tv.anime.model.anime.AnimeGroup
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.repository.AnimeRepository
import com.seiko.tv.anime.util.asResult
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import moe.tlaster.koin.get

@Composable
fun FeedAnimePresenter(
  event: Flow<FeedAnimeEvent>,
  tag: AnimeTab,
  repository: AnimeRepository = get(),
): FeedAnimeState {
  var retryKey by remember { mutableStateOf(1) }
  LaunchedEffect(Unit) {
    event.collect { event ->
      when (event) {
        FeedAnimeEvent.Retry -> {
          retryKey++
        }
      }
    }
  }
  val state by remember(retryKey) {
    repository.getFeeds(tag.uri).asResult().map { result ->
      result.fold(
        onSuccess = { FeedAnimeState.Success(it) },
        onFailure = {
          Napier.w(it) { "Feed animeList error: " }
          FeedAnimeState.Error(it.message ?: it.toString())
        }
      )
    }
  }.collectAsState(FeedAnimeState.Loading)
  return state
}

sealed interface FeedAnimeEvent {
  object Retry : FeedAnimeEvent
}

sealed interface FeedAnimeState {
  object Loading : FeedAnimeState

  data class Error(
    val message: String,
  ) : FeedAnimeState

  data class Success(
    val list: List<AnimeGroup>,
  ) : FeedAnimeState
}
