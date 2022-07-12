package com.seiko.tv.anime.ui.player

import com.seiko.compose.player.VideoPlayerSource
import com.seiko.tv.anime.data.repository.AnimeRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class PlayerViewModel(
  uri: String,
  repository: AnimeRepository
) : ViewModel() {

  val video: StateFlow<VideoPlayerSource?> = repository.getVideo(uri)
    .map { VideoPlayerSource.Network(it.playUrl) }
    .catch { Napier.w(it) { "Player animeVideo error: " } }
    .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
