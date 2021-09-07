package com.seiko.tv.anime.data.repository

import com.seiko.tv.anime.data.local.db.AnimeDataBase
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.data.model.anime.AnimeDetail
import com.seiko.tv.anime.data.model.anime.AnimeEpisode
import com.seiko.tv.anime.data.model.anime.AnimeGroup
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.model.anime.AnimeVideo
import com.seiko.tv.anime.data.service.SakuraService
import com.seiko.tv.anime.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
  private val service: SakuraService,
  private val dataBase: AnimeDataBase,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

  private val dbAnime by lazy { dataBase.animeDao() }

  fun getTabs(): Flow<List<AnimeTab>> {
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

  fun getFeeds(url: String): Flow<List<AnimeGroup>> {
    return flow {
      val response = service.getHomeResponse(url)
      emit(
        response.groups.mapIndexed { index, item ->
          AnimeGroup(
            response.titles[index],
            item.animes.map {
              Anime(
                title = it.title,
                cover = it.cover,
                uri = service.wrapUrl(it.href)
              )
            }
          )
        }
      )
    }.flowOn(ioDispatcher)
  }

  fun getDetail(url: String): Flow<AnimeDetail> {
    return flow {
      val response = service.getDetailResponse(url)
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
              uri = service.wrapUrl(episode.href),
            )
          },
          relatedList = response.relatedList.map { anime ->
            Anime(
              title = anime.title,
              cover = anime.cover,
              uri = service.wrapUrl(anime.href)
            )
          }
        )
      )
    }.flowOn(ioDispatcher)
  }

  fun getVideo(url: String): Flow<AnimeVideo> {
    return flow {
      val response = service.getVideoResponse(url)
      emit(
        AnimeVideo(
          playUrl = response.playUrl
        )
      )
    }.flowOn(ioDispatcher)
  }
}
