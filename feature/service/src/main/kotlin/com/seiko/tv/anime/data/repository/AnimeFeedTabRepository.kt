package com.seiko.tv.anime.data.repository

import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.service.SakuraService
import com.seiko.tv.anime.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeFeedTabRepository @Inject constructor(
  private val service: SakuraService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
  fun getAnimeTabList(): Flow<List<AnimeTab>> {
    return flow {
      val response = service.getHomeResponse()
      emit(
        response.tabs.map {
          AnimeTab(
            title = it.title,
            uri = service.wrapUrl(it.href)
          )
        }
      )
    }.flowOn(ioDispatcher)
  }
}
