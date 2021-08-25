package com.seiko.tv.anime.ui.player

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.compose.player.VideoPlayerSource
import com.seiko.tv.anime.data.AnimeVideoRepository
import com.seiko.tv.anime.di.assisted.ComposeAssistedFactory
import com.seiko.tv.anime.di.assisted.assistedViewModel
import com.seiko.tv.anime.model.AnimeVideo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import timber.log.Timber

class PlayerViewModel @AssistedInject constructor(
  @Assisted private val episode: String,
  repository: AnimeVideoRepository,
) : ViewModel() {

  val video: StateFlow<VideoPlayerSource?> = repository.getAnimeVideo(episode)
    .map { VideoPlayerSource.Network(it.playUrl) }
    .catch { Timber.w(it, "Player animeVideo error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, null)

  @dagger.assisted.AssistedFactory
  interface AssistedFactory : ComposeAssistedFactory {
    fun create(episode: String): PlayerViewModel
  }
}

@Composable
fun playerViewModel(episode: String): PlayerViewModel {
  return assistedViewModel<PlayerViewModel.AssistedFactory, PlayerViewModel> { factory ->
    factory.create(episode)
  }
}