package com.seiko.tv.anime.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.data.repository.AnimeRepository
import com.seiko.tv.anime.di.scope.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
  animeRepository: AnimeRepository,
  @IoDispatcher ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
  val favorites = Pager(config = PagingConfig(pageSize = 20)) {
    animeRepository.getFavorites()
  }.flow.map {
    it.map { item ->
      Anime(
        title = item.title,
        cover = item.cover,
        uri = item.uri,
      )
    }
  }.flowOn(ioDispatcher)
}
