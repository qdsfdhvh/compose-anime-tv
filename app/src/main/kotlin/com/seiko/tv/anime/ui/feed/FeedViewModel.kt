package com.seiko.tv.anime.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.model.anime.AnimeGroup
import com.seiko.tv.anime.data.repository.AnimeHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
  repository: AnimeHomeRepository
) : ViewModel() {

  val tabList: StateFlow<List<String>> = flow {
    emit(
      listOf(
        "首页", "日本动漫", "国产动漫", "美国动漫", "动漫电影", "亲子动漫",
        "新番动漫", "剧场版", "OVA版", "真人版", "动漫专题", "最近更新",
      )
    )
  }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  val animeList: StateFlow<List<AnimeGroup>> = repository.getAnimeList()
    .catch { Timber.w(it, "Home animeList error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
