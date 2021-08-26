package com.seiko.tv.anime.ui.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.AnimeDetailRepository
import com.seiko.tv.anime.di.assisted.assistedViewModel
import com.seiko.tv.anime.model.AnimeDetail
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  @Assisted private val animeId: Int,
  repository: AnimeDetailRepository,
) : ViewModel() {

  val detail: StateFlow<AnimeDetail> = repository.getAnimeDetail(animeId)
    .catch { Timber.w(it, "Detail animeDetail error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, AnimeDetail())

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(animeId: Int): DetailViewModel
  }
}

@Composable
fun detailViewModel(animeId: Int): DetailViewModel {
  return assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(animeId)
  }
}
