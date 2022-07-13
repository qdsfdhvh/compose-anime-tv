package com.seiko.tv.anime.ui.player

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.compose.player.VideoPlayerSource
import com.seiko.tv.anime.data.repository.AnimeRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import moe.tlaster.koin.get

@SuppressLint("ComposableNaming")
@Composable
fun PlayerPresenter(
  uri: String,
  repository: AnimeRepository = get(),
): PlayerState {
  var source by remember { mutableStateOf<VideoPlayerSource?>(null) }
  LaunchedEffect(uri) {
    repository.getVideo(uri)
      .catch { Napier.w(it) { "Player animeVideo error: " } }
      .collect {
        source = VideoPlayerSource.Network(it.playUrl)
      }
  }
  return when {
    source != null -> PlayerState.Success(source!!)
    else -> PlayerState.Loading
  }
}

sealed interface PlayerState {
  object Loading : PlayerState
  data class Success(val source: VideoPlayerSource) : PlayerState
}
