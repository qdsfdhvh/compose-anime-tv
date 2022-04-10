package com.seiko.compose.player.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.seiko.compose.player.LocalVideoPlayerController

@Composable
fun MediaPlayerLayout(player: Player, modifier: Modifier = Modifier) {
  val controller = LocalVideoPlayerController.current
  val state by controller.state.collectAsState()

  val lifecycle = LocalLifecycleOwner.current.lifecycle

  PlayerSurface(modifier) { playerView ->
    playerView.player = player

    lifecycle.addObserver(object : LifecycleEventObserver {
      override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
          Lifecycle.Event.ON_START -> {
            playerView.keepScreenOn = true
            playerView.onResume()
            if (state.isPlaying) {
              controller.play()
            }
          }
          Lifecycle.Event.ON_STOP -> {
            playerView.keepScreenOn = false
            playerView.onPause()
            controller.pause()
          }
          else -> Unit
        }
      }
    })
  }

  DisposableEffect(Unit) {
    onDispose {
      player.release()
    }
  }
}

@Composable
fun PlayerSurface(
  modifier: Modifier = Modifier,
  onPlayerViewAvailable: (StyledPlayerView) -> Unit = {}
) {
  AndroidView(
    modifier = modifier,
    factory = { context ->
      StyledPlayerView(context).apply {
        useController = false
        onPlayerViewAvailable(this)
      }
    }
  )
}
