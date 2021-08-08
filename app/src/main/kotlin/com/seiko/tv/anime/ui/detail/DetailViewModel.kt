package com.seiko.tv.anime.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.AnimeDetailRepository
import com.seiko.tv.anime.di.assisted.ComposeAssistedFactory
import com.seiko.tv.anime.model.AnimeDetail
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  @Assisted private val animeId: Int,
  repository: AnimeDetailRepository,
) : ViewModel() {

  val detail: StateFlow<AnimeDetail> = repository.getAnimeDetail(animeId)
    .onEach { Timber.d(it.toString()) }
    .catch { Timber.w(it, "Detail animeDetail error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, AnimeDetail())

  @dagger.assisted.AssistedFactory
  interface AssistedFactory : ComposeAssistedFactory {
    fun create(animeId: Int): DetailViewModel
  }
}