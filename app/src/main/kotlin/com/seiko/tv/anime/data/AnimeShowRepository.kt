package com.seiko.tv.anime.data

import com.seiko.tv.anime.http.YydmService
import com.seiko.tv.anime.model.Anime
import com.seiko.tv.anime.model.AnimeGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimeShowRepository @Inject constructor(private val service: YydmService) {

  @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
  fun getAnimeList(path: String = ""): Flow<List<AnimeGroup>> {
    return flow {
      val response = service.getHomeResponse(path)
      emit(response.groups.mapIndexed { index, item ->
        AnimeGroup(response.titles[index], item.animes.map {
          Anime(
            title = it.title,
            cover = it.cover,
            actionUrl = it.actionUrl
          )
        })
      })
    }.flowOn(Dispatchers.IO)
  }
}