package com.seiko.tv.anime.ui.favorite

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.repository.AnimeRepository
import moe.tlaster.koin.get

@SuppressLint("ComposableNaming")
@Composable
fun FavoritePresenter(
  repository: AnimeRepository = get(),
): FavoriteState {
  val scope = rememberCoroutineScope()
  val pagingItems = remember(scope) {
    Pager(PagingConfig(pageSize = 20)) {
      repository.getFavorites()
    }.flow.cachedIn(scope)
  }.collectAsLazyPagingItems()
  return FavoriteState.Success(pagingItems)
}

sealed interface FavoriteState {
  data class Success(val pagingItems: LazyPagingItems<Anime>) : FavoriteState
}
