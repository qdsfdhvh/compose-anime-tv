package com.seiko.tv.anime.data

import com.seiko.tv.anime.http.YhdmService
import com.seiko.tv.anime.model.AnimeDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeDetailRepository @Inject constructor(private val service: YhdmService) {
  fun getAnimeDetail(animeId: Int): Flow<AnimeDetail> {
    return flow {
      val response = service.getDetailResponse(animeId)
      emit(
        AnimeDetail(
          title = response.title,
          cover = response.cover,
          alias = response.alias,
          rating = response.rating,
          releaseTime = response.releaseTime,
          area = response.area,
          types = response.types,
          tags = response.tags,
          indexes = response.indexes,
          state = response.state,
          description = response.description,
        )
      )
    }
  }
}