package com.seiko.compose.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

expect interface Player

@Composable
expect fun rememberVideoPlayerController(
  player: Player,
  scope: CoroutineScope,
): VideoPlayerController

@Composable
expect fun TvVideoPlayer(
  player: Player,
  controller: VideoPlayerController,
  modifier: Modifier = Modifier
)
