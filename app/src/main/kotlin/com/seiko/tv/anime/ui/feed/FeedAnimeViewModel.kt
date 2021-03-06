package com.seiko.tv.anime.ui.feed

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.model.anime.AnimeGroup
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class FeedAnimeViewModel(
  tab: AnimeTab,
  repository: AnimeRepository,
) : ViewModel() {

  val animeList: StateFlow<List<AnimeGroup>> = repository.getFeeds(tab.uri)
    .catch { Timber.w(it, "Feed animeList error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

@Composable
fun feedAnimeViewModel(tab: AnimeTab): FeedAnimeViewModel {
  return getViewModel {
    parametersOf(tab)
  }
}
