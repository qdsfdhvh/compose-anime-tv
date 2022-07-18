package com.seiko.compose.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

actual interface Player

@Composable
actual fun rememberVideoPlayerController(
  player: Player,
  scope: CoroutineScope,
): VideoPlayerController {
  TODO("Not yet implemented")
}

@Composable
actual fun TvVideoPlayer(
  player: Player,
  controller: VideoPlayerController,
  modifier: Modifier
) {
}
