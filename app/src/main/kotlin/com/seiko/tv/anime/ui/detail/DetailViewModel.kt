package com.seiko.tv.anime.ui.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.model.anime.AnimeDetail
import com.seiko.tv.anime.data.repository.AnimeRepository
import com.seiko.tv.anime.ui.composer.assisted.assistedViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  @Assisted private val uri: String,
  repository: AnimeRepository,
) : ViewModel() {

  val detail: StateFlow<AnimeDetail> = repository.getDetail(uri)
    .catch { Timber.w(it, "Detail animeDetail error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, AnimeDetail.Empty)

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(uri: String): DetailViewModel
  }
}

@Composable
fun detailViewModel(uri: String): DetailViewModel {
  return assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(uri)
  }
}
