package com.seiko.compose.player


data class VideoPlayerState constructor(
  val isPlaying: Boolean = true,
  val controlsVisible: Boolean = false,
  val controlsEnabled: Boolean = true,
  val duration: Long = 1L,
  val currentPosition: Long = 1L,
  val bufferedPosition: Long = 1L,
  val videoSize: Pair<Int, Int> = 1920 to 1080,
  val playbackState: PlaybackState = PlaybackState.STATE_IDLE,
  val seekDirection: VideoSeekDirection = VideoSeekDirection.NONE
)
