package com.seiko.compose.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect interface Player

@Composable
expect fun rememberPlayer(
  source: VideoPlayerSource
): Player

@Composable
expect fun rememberVideoPlayerController(
  player: Player,
): VideoPlayerController

@Composable
expect fun TvVideoPlayer(
  player: Player,
  controller: VideoPlayerController,
  modifier: Modifier = Modifier
)