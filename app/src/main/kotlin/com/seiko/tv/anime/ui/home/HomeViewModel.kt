package com.seiko.tv.anime.ui.home

import androidx.lifecycle.ViewModel
import com.seiko.tv.anime.data.AnimeShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  repository: AnimeShowRepository
) : ViewModel() {
  val tabList = flow {
    emit(listOf(
      "首页",
      "日本动漫",
      "国产动漫",
      "美国动漫",
      "动漫电影",
      "亲子动漫",
      "新番动漫",
      "剧场版",
      "OVA版",
      "真人版",
      "动漫专题",
      "最近更新",
    ))
  }
  val animeList = repository.getAnimeList()
    .catch { Timber.w(it, "Home animeList error: ") }
}