package com.seiko.compose.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual interface Player

@Composable
actual fun rememberPlayer(
  source: VideoPlayerSource,
): Player {
  TODO("Not yet implemented")
}

@Composable
actual fun rememberVideoPlayerController(
  player: Player,
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