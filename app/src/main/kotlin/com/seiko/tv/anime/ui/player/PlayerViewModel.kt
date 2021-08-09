package com.seiko.tv.anime.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.AnimeVideoRepository
import com.seiko.tv.anime.di.assisted.ComposeAssistedFactory
import com.seiko.tv.anime.model.AnimeVideo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class PlayerViewModel @AssistedInject constructor(
  @Assisted private val episode: String,
  repository: AnimeVideoRepository,
) : ViewModel() {

  val video: StateFlow<AnimeVideo> = repository.getAnimeVideo(episode)
    .catch { Timber.w(it, "Player animeVideo error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, AnimeVideo())

  @dagger.assisted.AssistedFactory
  interface AssistedFactory : ComposeAssistedFactory {
    fun create(episode: String): PlayerViewModel
  }
}