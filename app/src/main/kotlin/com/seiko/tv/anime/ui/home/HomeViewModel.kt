package com.seiko.tv.anime.ui.home

import androidx.lifecycle.ViewModel
import com.seiko.tv.anime.data.AnimeShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  repository: AnimeShowRepository
) : ViewModel() {
  val animeList = flow {
    emitAll(repository.getAnimeList())
  }
}