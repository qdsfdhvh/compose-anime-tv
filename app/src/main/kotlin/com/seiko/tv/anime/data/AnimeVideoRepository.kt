package com.seiko.tv.anime.data

import com.seiko.tv.anime.http.YhdmService
import com.seiko.tv.anime.model.AnimeVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeVideoRepository @Inject constructor(private val service: YhdmService) {
  fun getAnimeVideo(episode: String): Flow<AnimeVideo> {
    return flow {
      val response = service.getDetailResponse(episode)
      emit(
        AnimeVideo(
          playUrl = response.playUrl
        )
      )
    }
  }
}
