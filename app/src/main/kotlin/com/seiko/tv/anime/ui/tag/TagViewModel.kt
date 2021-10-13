package com.seiko.tv.anime.ui.tag

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seiko.tv.anime.data.repository.AnimeRepository
import com.seiko.tv.anime.ui.composer.assisted.assistedViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class TagViewModel @AssistedInject constructor(
  @Assisted uri: String,
  repository: AnimeRepository,
) : ViewModel() {

  val animes = Pager(PagingConfig(pageSize = 20)) {
    TagPagingSource(uri, repository)
  }.flow.cachedIn(viewModelScope)

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(url: String): TagViewModel
  }
}

@Composable
fun tagViewModel(uri: String): TagViewModel {
  return assistedViewModel<TagViewModel.AssistedFactory, TagViewModel>(uri) { factory ->
    factory.create(uri)
  }
}
