package com.seiko.tv.anime.data.repository

import com.seiko.tv.anime.data.local.db.AnimeDataBase
import com.seiko.tv.anime.data.local.db.model.DbAnime
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.data.model.anime.AnimeDetail
import com.seiko.tv.anime.data.model.anime.AnimeEpisode
import com.seiko.tv.anime.data.model.anime.AnimeGroup
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.data.model.anime.AnimeTimeLine
import com.seiko.tv.anime.data.model.anime.AnimeTimeLineGroup
import com.seiko.tv.anime.data.model.anime.AnimeVideo
import com.seiko.tv.anime.data.service.SakuraService
import com.seiko.tv.anime.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
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

  suspend fun getDetail(url: String): AnimeDetail {
    return withContext(ioDispatcher) {
      val response = service.getDetailResponse(url)
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
        },
        uri = url,
      )
    }
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

  suspend fun isFavoriteAnime(uri: String): Boolean {
    return withContext(ioDispatcher) {
      dbAnime.contains(uri) > 0
    }
  }

  suspend fun insertFavoriteAnime(anime: AnimeDetail): Boolean {
    return withContext(ioDispatcher) {
      val current = System.currentTimeMillis()
      dbAnime.insert(
        DbAnime(
          id = 0,
          title = anime.title,
          cover = anime.cover,
          uri = anime.uri,
          updateAt = current,
          createAt = current
        )
      )
      true
    }
  }

  suspend fun removeFavoriteAnime(uri: String): Boolean {
    return withContext(ioDispatcher) {
      dbAnime.delete(uri) > 0
    }
  }

  fun getFavorites() = dbAnime.findAll()

  fun getTimeLine(): Flow<List<AnimeTimeLineGroup>> {
    return flow {
      val response = service.getTimeLineResponse()
      emit(response.tagAnimesList.mapIndexed { index, tagAnimes ->
        AnimeTimeLineGroup(
          title = response.tags[index],
          animes = tagAnimes.animes.map { anime ->
            AnimeTimeLine(
              title = anime.title,
              uri = service.wrapUrl(anime.href),
              body = anime.body,
              bodyUri = service.wrapUrl(anime.bodyHref)
            )
          }
        )
      })
    }.flowOn(ioDispatcher)
  }
}
