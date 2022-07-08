package com.seiko.tv.anime.ui.tag

import androidx.compose.runtime.Composable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seiko.tv.anime.data.repository.AnimeRepository
import moe.tlaster.koin.compose.getViewModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.koin.core.parameter.parametersOf

class TagViewModel(
  uri: String,
  repository: AnimeRepository
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
