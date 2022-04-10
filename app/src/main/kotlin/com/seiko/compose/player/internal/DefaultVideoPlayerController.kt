package com.seiko.compose.player.internal

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoSize
import com.seiko.compose.player.VideoPlayerAction
import com.seiko.compose.player.VideoPlayerController
import com.seiko.compose.player.VideoPlayerState
import com.seiko.compose.player.VideoSeekDirection
import com.seiko.compose.player.stateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class DefaultVideoPlayerController(
  private val player: Player,
  private val coroutineScope: CoroutineScope,
  initialState: VideoPlayerState,
) : VideoPlayerController {

  private val intents = MutableSharedFlow<VideoPlayerAction>(
    extraBufferCapacity = 20,
    onBufferOverflow = BufferOverflow.SUSPEND
  )

  private val _state = intents.asSharedFlow()
    .scan(initialState, ::stateReducer)
    .flowOn(Dispatchers.IO)
    .stateIn(coroutineScope, SharingStarted.Lazily, initialState)

  override val state: StateFlow<VideoPlayerState>
    get() = _state

  val currentState: VideoPlayerState
    get() = _state.value

  override val isPlaying: Boolean
    get() = player.playWhenReady

  private val playerListener = object : Player.Listener {

    override fun onPlaybackStateChanged(playbackState: Int) {
      if (playbackState == Player.STATE_READY) {
        seekFinish()
        updateProgress()
      }
      updatePlaybackState(playbackState)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
      updatePlayState(playWhenReady)
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
      updateVideoSize(videoSize.width, videoSize.height)
    }
  }

  init {
    player.playWhenReady = initialState.isPlaying
    player.addListener(playerListener)
  }

  override fun play() {
    player.playWhenReady = true
  }

  override fun pause() {
    player.playWhenReady = false
  }

  override fun playToggle() {
    if (player.isPlaying) pause()
    else play()
  }

  override fun seekRewind() {
    val target = (player.currentPosition - 10_000).coerceAtLeast(0)
    player.seekTo(target)
    updateDurationAndPosition()
    updateSeekAction(VideoSeekDirection.Rewind)
  }

  override fun seekForward() {
    val target = (player.currentPosition + 10_000).coerceAtMost(player.duration)
    player.seekTo(target)
    updateDurationAndPosition()
    updateSeekAction(VideoSeekDirection.Forward)
  }

  override fun seekFinish() {
    updateSeekAction(VideoSeekDirection.NONE)
  }

  override fun seekTo(positionMs: Long) {
    player.seekTo(positionMs)
  }

  override fun reset() {
    player.stop()
  }

  override fun showControl() {
    intents.tryEmit(VideoPlayerAction.ControlsVisible(true))
  }

  override fun hideControl() {
    intents.tryEmit(VideoPlayerAction.ControlsVisible(false))
  }

  private var updateProgressJob: Job? = null
  private fun updateProgress() {
    updateProgressJob?.cancel()
    updateProgressJob = coroutineScope.launch {
      while (isActive) {
        updateDurationAndPosition()
        delay(250)
      }
    }
  }

  private fun updateDurationAndPosition() {
    intents.tryEmit(
      VideoPlayerAction.Progress(
        duration = player.duration.coerceAtLeast(0L),
        currentPosition = player.currentPosition.coerceAtLeast(0L),
        bufferedPosition = player.bufferedPosition.coerceAtLeast(0L),
      )
    )
  }

  private fun updatePlaybackState(playbackState: Int) {
    intents.tryEmit(VideoPlayerAction.PlaybackState(playbackState))
  }

  private fun updatePlayState(playWhenReady: Boolean) {
    intents.tryEmit(VideoPlayerAction.PlayState(playWhenReady))
  }

  private fun updateVideoSize(width: Int, height: Int) {
    intents.tryEmit(VideoPlayerAction.VideoSize(width to height))
  }

  private fun updateSeekAction(seekAction: VideoSeekDirection) {
    intents.tryEmit(VideoPlayerAction.SeekDirection(seekAction))
  }
}
