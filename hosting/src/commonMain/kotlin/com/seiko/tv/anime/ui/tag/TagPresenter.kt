package com.seiko.tv.anime.ui.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.tv.anime.model.anime.AnimeTagPageItem
import com.seiko.tv.anime.repository.AnimeRepository
import moe.tlaster.koin.get

@Composable
fun TagPresenter(
  uri: String,
  repository: AnimeRepository = get(),
): TagState {
  val scope = rememberCoroutineScope()
  val pagingItems = remember(uri, scope) {
    Pager(PagingConfig(pageSize = 20)) {
      TagPagingSource(uri, repository)
    }.flow.cachedIn(scope)
  }.collectAsLazyPagingItems()
  return TagState.Success(pagingItems)
}

sealed interface TagState {
  data class Success(val pagingItems: LazyPagingItems<AnimeTagPageItem>) : TagState
}