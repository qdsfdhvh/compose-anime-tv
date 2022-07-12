package com.seiko.tv.anime.ui.feed

import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.repository.AnimeRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class FeedViewModel(
  repository: AnimeRepository
) : ViewModel() {

  val tabs: StateFlow<List<AnimeTab>> = repository.getTabs()
    .catch { Napier.w(it) { "Feed tabs error: " } }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
