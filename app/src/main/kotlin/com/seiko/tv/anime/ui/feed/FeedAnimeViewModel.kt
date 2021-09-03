package com.seiko.tv.anime.ui.feed

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.model.anime.AnimeGroup
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.repository.AnimeHomeRepository
import com.seiko.tv.anime.ui.composer.assisted.assistedViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class FeedAnimeViewModel @AssistedInject constructor(
  @Assisted private val tab: AnimeTab,
  repository: AnimeHomeRepository,
) : ViewModel() {

  val animeList: StateFlow<List<AnimeGroup>> = repository.getAnimeList(tab.uri)
    .catch { Timber.w(it, "Feed animeList error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(tab: AnimeTab): FeedAnimeViewModel
  }
}

@Composable
fun feedAnimeViewModel(tab: AnimeTab): FeedAnimeViewModel {
  return assistedViewModel<FeedAnimeViewModel.AssistedFactory, FeedAnimeViewModel>(tab) { factory ->
    factory.create(tab)
  }
}
