package com.seiko.compose.player

import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerController {
  val state: StateFlow<VideoPlayerState>
  val isPlaying: Boolean
  fun play()
  fun pause()
  fun playToggle()
  fun reset()
  fun seekTo(positionMs: Long)
  fun seekForward()
  fun seekRewind()
  fun seekFinish()
  fun showControl()
  fun hideControl()
}
