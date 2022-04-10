package com.seiko.compose.player

sealed class VideoPlayerAction {
  data class Progress(
    val duration: Long,
    val currentPosition: Long,
    val bufferedPosition: Long,
  ) : VideoPlayerAction()

  data class ControlsVisible(val isVisible: Boolean) : VideoPlayerAction()
  data class PlaybackState(val playbackState: Int) : VideoPlayerAction()
  data class PlayState(val isPlaying: Boolean) : VideoPlayerAction()
  data class VideoSize(val videoSize: Pair<Int, Int>) : VideoPlayerAction()
  data class SeekDirection(val seekDirection: VideoSeekDirection) : VideoPlayerAction()
}

fun stateReducer(state: VideoPlayerState, action: VideoPlayerAction): VideoPlayerState {
  return when (action) {
    is VideoPlayerAction.Progress -> state.copy(
      duration = action.duration,
      currentPosition = action.currentPosition,
      bufferedPosition = action.bufferedPosition
    )
    is VideoPlayerAction.ControlsVisible -> state.copy(controlsVisible = action.isVisible)
    is VideoPlayerAction.PlaybackState -> state.copy(playbackState = action.playbackState)
    is VideoPlayerAction.PlayState -> state.copy(isPlaying = action.isPlaying)
    is VideoPlayerAction.VideoSize -> state.copy(videoSize = action.videoSize)
    is VideoPlayerAction.SeekDirection -> state.copy(seekDirection = action.seekDirection)
  }
}
