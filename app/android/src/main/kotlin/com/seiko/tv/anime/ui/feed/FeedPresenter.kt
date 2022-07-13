package com.seiko.tv.anime.ui.feed

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.repository.AnimeRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import moe.tlaster.koin.get

@SuppressLint("ComposableNaming")
@Composable
fun FeedPresenter(
  repository: AnimeRepository = get(),
): FeedState {
  var list by remember { mutableStateOf<List<AnimeTab>?>(null) }
  LaunchedEffect(Unit) {
    repository.getTabs()
      .catch { Napier.w(it) { "Feed tabs error: " } }
      .collect {
        list = it
      }
  }
  return when {
    list != null -> FeedState.Success(list!!)
    else -> FeedState.Loading
  }
}

sealed interface FeedState {
  object Loading : FeedState
  data class Success(val list: List<AnimeTab>) : FeedState
}
