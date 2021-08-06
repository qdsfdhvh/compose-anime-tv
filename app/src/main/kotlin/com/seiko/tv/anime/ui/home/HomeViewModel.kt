package com.seiko.tv.anime.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.AnimeHomeRepository
import com.seiko.tv.anime.model.AnimeGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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