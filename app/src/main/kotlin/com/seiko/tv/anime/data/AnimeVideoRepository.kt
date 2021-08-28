package com.seiko.tv.anime.data

import com.seiko.tv.anime.http.YhdmService
import com.seiko.tv.anime.model.AnimeVideo
import com.seiko.tv.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeVideoRepository @Inject constructor(
  private val service: YhdmService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
  fun getAnimeVideo(episode: String): Flow<AnimeVideo> {
    return flow {
      val response = service.getDetailResponse(episode)
      emit(
        AnimeVideo(
          playUrl = response.playUrl
        )
      )
    }.flowOn(ioDispatcher)
  }
}
