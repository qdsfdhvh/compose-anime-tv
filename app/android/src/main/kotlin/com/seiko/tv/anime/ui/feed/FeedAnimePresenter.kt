package com.seiko.tv.anime.ui.feed

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.tv.anime.model.anime.AnimeGroup
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.repository.AnimeRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import moe.tlaster.koin.get

@SuppressLint("ComposableNaming")
@Composable
fun FeedAnimePresenter(
  tag: AnimeTab,
  repository: AnimeRepository = get(),
): FeedAnimeState {
  var list by remember { mutableStateOf<List<AnimeGroup>?>(null) }
  LaunchedEffect(tag) {
    repository.getFeeds(tag.uri)
      .catch { Napier.w(it) { "Feed animeList error: " } }
      .collect {
        list = it
      }
  }
  return when {
    list != null -> FeedAnimeState.Success(list!!)
    else -> FeedAnimeState.Loading
  }
}

sealed interface FeedAnimeState {
  object Loading : FeedAnimeState
  data class Success(val list: List<AnimeGroup>) : FeedAnimeState
}
