package com.seiko.compose.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.seiko.compose.player.internal.DefaultVideoPlayerController
import com.seiko.compose.player.ui.MediaControlKeyEvent
import com.seiko.compose.player.ui.MediaControlLayout
import com.seiko.compose.player.ui.MediaPlayerLayout
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.koin.get

actual typealias Player = com.google.android.exoplayer2.Player

@Composable
actual fun rememberVideoPlayerController(
  player: Player,
  scope: CoroutineScope,
): VideoPlayerController {
  return rememberSaveable(
    player,
    scope,
    saver = object : Saver<DefaultVideoPlayerController, VideoPlayerState> {
      override fun restore(value: VideoPlayerState): DefaultVideoPlayerController {
        return DefaultVideoPlayerController(
          player = player,
          initialState = value,
          coroutineScope = scope,
        )
      }

      override fun SaverScope.save(value: DefaultVideoPlayerController): VideoPlayerState {
        return value.currentState
      }
    },
    init = {
      DefaultVideoPlayerController(
        player = player,
        initialState = VideoPlayerState(),
        coroutineScope = scope,
      )
    }
  )
}

@Composable
actual fun TvVideoPlayer(
  player: Player,
  controller: VideoPlayerController,
  modifier: Modifier,
) {
  val state by controller.state.collectAsState()
  Box(modifier = modifier.background(Color.Black)) {
    MediaPlayerLayout(
      player = player,
      controller = controller,
      state = state,
      modifier = Modifier.matchParentSize(),
    )
    MediaControlLayout(
      state = state,
      modifier = Modifier.matchParentSize(),
    )
    MediaControlKeyEvent(
      controller = controller,
      state = state,
      modifier = Modifier.matchParentSize(),
    )
  }
}
