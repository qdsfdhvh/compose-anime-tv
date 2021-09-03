package com.seiko.tv.anime.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.repository.AnimeFeedTabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
  repository: AnimeFeedTabRepository
) : ViewModel() {

  val tabs: StateFlow<List<AnimeTab>> = repository.getAnimeTabList()
    .catch { Timber.w(it, "Feed tabs error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
