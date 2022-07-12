package com.seiko.tv.anime.ui.tag

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seiko.tv.anime.data.repository.AnimeRepository
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class TagViewModel(
  uri: String,
  repository: AnimeRepository
) : ViewModel() {

  val animes = Pager(PagingConfig(pageSize = 20)) {
    TagPagingSource(uri, repository)
  }.flow.cachedIn(viewModelScope)
}
