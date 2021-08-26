package com.seiko.tv.anime.data

import com.seiko.tv.anime.http.YhdmService
import com.seiko.tv.anime.model.Anime
import com.seiko.tv.anime.model.AnimeDetail
import com.seiko.tv.anime.model.AnimeEpisode
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
    }
  }
}
