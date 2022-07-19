package com.seiko.tv.anime.ui.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.repository.AnimeRepository
import com.seiko.tv.anime.util.asResult
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import moe.tlaster.koin.get

@Composable
fun FeedPresenter(
  event: Flow<FeedEvent>,
  repository: AnimeRepository = get(),
): FeedState {
  var retryKey by remember { mutableStateOf(1) }
  LaunchedEffect(Unit) {
    event.collect { event ->
      when (event) {
        FeedEvent.Retry -> {
          retryKey++
        }
      }
    }
  }
  val state by remember(retryKey) {
    repository.getTabs().asResult().map { result ->
      result.fold(
        onSuccess = { FeedState.Success(it) },
        onFailure = {
          Napier.w(it) { "Feed tabs error: " }
          FeedState.Error(it.message ?: it.toString())
        }
      )
    }
  }.collectAsState(FeedState.Loading)
  return state
}

sealed interface FeedEvent {
  object Retry : FeedEvent
}

sealed interface FeedState {
  object Loading : FeedState

  data class Error(
    val message: String,
  ) : FeedState

  data class Success(
    val list: List<AnimeTab>,
  ) : FeedState
}
