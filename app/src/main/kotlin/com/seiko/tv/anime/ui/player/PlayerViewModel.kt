package com.seiko.tv.anime.ui.player

import androidx.compose.runtime.Composable
import com.seiko.compose.player.VideoPlayerSource
import com.seiko.tv.anime.data.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.koin.getViewModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class PlayerViewModel(
  uri: String,
  repository: AnimeRepository
) : ViewModel() {

  val video: StateFlow<VideoPlayerSource?> = repository.getVideo(uri)
    .map { VideoPlayerSource.Network(it.playUrl) }
    .catch { Timber.w(it, "Player animeVideo error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, null)
}

@Composable
fun playerViewModel(uri: String): PlayerViewModel {
  return getViewModel {
    parametersOf(uri)
  }
}
