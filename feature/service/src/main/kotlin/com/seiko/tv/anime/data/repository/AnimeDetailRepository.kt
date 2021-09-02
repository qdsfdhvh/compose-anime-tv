package com.seiko.tv.anime.data.repository

import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.data.model.anime.AnimeDetail
import com.seiko.tv.anime.data.model.anime.AnimeEpisode
import com.seiko.tv.anime.data.service.YhdmService
import com.seiko.tv.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeDetailRepository @Inject constructor(
  private val service: YhdmService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
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
          episodeList = response.episodeList.map { episode ->
            AnimeEpisode(
              title = episode.title,
              actionUrl = episode.actionUrl,
            )
          },
          relatedList = response.relatedList.map { anime ->
            Anime(
              title = anime.title,
              cover = anime.cover,
              actionUrl = anime.actionUrl
            )
          }
        )
      )
    }.flowOn(ioDispatcher)
  }
}
