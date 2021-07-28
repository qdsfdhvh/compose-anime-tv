package com.seiko.tv.anime.ui.home

import androidx.lifecycle.ViewModel
import com.seiko.tv.anime.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

  val animeList = flow {
    emit(
      listOf(
        Anime(12241, "干支魂 猫客万来", "https://ddcdn-img.acplay.net/anime/12241.jpg!client"),
        Anime(14136, "B: The Beginning 第二季", "https://ddcdn-img.acplay.net/anime/14136.jpg!client"),
        Anime(14482, "武士弥助", "https://ddcdn-img.acplay.net/anime/14482.jpg!client"),
        Anime(14556, "Thunderbolt Fantasy 东离剑游纪 3", "https://ddcdn-img.acplay.net/anime/14556.jpg!client"),
        Anime(14825, "EDEN (2020)", "https://ddcdn-img.acplay.net/anime/14825.jpg!client"),
        Anime(15028, "佐贺偶像是传奇 复仇", "https://ddcdn-img.acplay.net/anime/15028.jpg!client"),
      )
    )
  }

}