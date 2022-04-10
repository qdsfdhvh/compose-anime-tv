package com.seiko.tv.anime.ui.tag

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seiko.tv.anime.data.repository.AnimeRepository
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

class TagViewModel(
  uri: String,
  repository: AnimeRepository,
) : ViewModel() {

  val animes = Pager(PagingConfig(pageSize = 20)) {
    TagPagingSource(uri, repository)
  }.flow.cachedIn(viewModelScope)

}

@Composable
fun tagViewModel(uri: String): TagViewModel {
  return getViewModel {
    parametersOf(uri)
  }
}
