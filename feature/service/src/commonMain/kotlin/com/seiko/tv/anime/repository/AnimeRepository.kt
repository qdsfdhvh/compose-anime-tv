package com.seiko.tv.anime.repository

import androidx.paging.PagingSource
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.paging3.QueryPagingSource
import com.seiko.tv.anime.db.AppDatabase
import com.seiko.tv.anime.db.DbAnime
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.model.anime.AnimeDetail
import com.seiko.tv.anime.model.anime.AnimeEpisode
import com.seiko.tv.anime.model.anime.AnimeGroup
import com.seiko.tv.anime.model.anime.AnimeTab
import com.seiko.tv.anime.model.anime.AnimeTag
import com.seiko.tv.anime.model.anime.AnimeTagPage
import com.seiko.tv.anime.model.anime.AnimeTagPageItem
import com.seiko.tv.anime.model.anime.AnimeTimeLine
import com.seiko.tv.anime.model.anime.AnimeTimeLineGroup
import com.seiko.tv.anime.model.anime.AnimeVideo
import com.seiko.tv.anime.service.SakuraService
import com.seiko.tv.anime.util.flatMap
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnimeRepository(
  private val service: SakuraService,
  private val dataBase: AppDatabase,
  private val ioDispatcher: CoroutineDispatcher
) {

  private val dbAnime get() = dataBase.animeQueries

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
          releaseTime = response.tags.find { it.tip.startsWith("上映") }?.run {
            titles.joinToString { it }
          } ?: "",
          area = response.tags.find { it.tip.startsWith("地区") }?.run {
            titles.joinToString { it }
          } ?: "",
          types = response.tags.find { it.tip.startsWith("类型") }?.run {
            titles.mapIndexed { index, title ->
              AnimeTag(
                title = title,
                uri = service.wrapUrl(hrefs[index])
              )
            }
          }.orEmpty(),
          tags = response.tags.find { it.tip.startsWith("标签") }?.run {
            titles.mapIndexed { index, title ->
              AnimeTag(
                title = title,
                uri = service.wrapUrl(hrefs[index])
              )
            }
          }.orEmpty(),
          indexes = response.tags.find { it.tip.startsWith("索引") }?.run {
            titles.mapIndexed { index, title ->
              AnimeTag(
                title = title,
                uri = service.wrapUrl(hrefs[index])
              )
            }
          }.orEmpty(),
          description = response.description,
          episodeList = response.episodeList.map { episode ->
            AnimeEpisode(
              title = episode.title,
              uri = service.wrapUrl(episode.href)
            )
          },
          relatedList = response.relatedList.map { anime ->
            Anime(
              title = anime.title,
              cover = anime.cover,
              uri = service.wrapUrl(anime.href)
            )
          },
          uri = url
        )
      )
    }.flowOn(ioDispatcher)
  }

  suspend fun getTags(url: String): AnimeTagPage {
    val response = service.getTagResponse(url)
    return AnimeTagPage(
      title = response.title,
      animes = response.animes.map { anime ->
        AnimeTagPageItem(
          title = anime.title,
          cover = anime.cover,
          uri = service.wrapUrl(anime.href),
          update = anime.update,
          tags = anime.tags.run {
            titles.mapIndexed { index, title ->
              AnimeTag(
                title = title,
                uri = service.wrapUrl(hrefs[index])
              )
            }
          },
          description = anime.description
        )
      }
    )
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

  fun isFavoriteAnime(uri: String): Flow<Boolean> {
    return dbAnime.contains(uri).asFlow().map { it.executeAsOne() > 0 }
  }

  suspend fun insertFavoriteAnime(anime: AnimeDetail): Boolean {
    return withContext(ioDispatcher) {
      val current = getTimeMillis()
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
      dbAnime.delete(uri)
      true
    }
  }

  fun getFavorites(): PagingSource<Long, Anime> {
    return QueryPagingSource(
      dispatcher = ioDispatcher,
      transacter = dataBase.animeQueries,
      countQuery = dbAnime.getFavoritesCount(),
      queryProvider = { limit, offset ->
        dbAnime.getFavorites(limit, offset).flatMap { item ->
          Anime(
            title = item.title,
            cover = item.cover,
            uri = item.uri
          )
        }
      },
    )
  }

  fun getTimeLine(): Flow<List<AnimeTimeLineGroup>> {
    return flow {
      val response = service.getTimeLineResponse()
      emit(
        response.tagAnimesList.mapIndexed { index, tagAnimes ->
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
        }
      )
    }.flowOn(ioDispatcher)
  }
}
