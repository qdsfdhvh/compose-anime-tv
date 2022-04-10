package com.seiko.compose.player.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.seiko.compose.player.LocalVideoPlayerController

@Composable
fun MediaPlayerLayout(player: Player, modifier: Modifier = Modifier) {
  val controller = LocalVideoPlayerController.current
  val state by controller.state.collectAsState()

  val lifecycle = LocalLifecycleOwner.current.lifecycle

  PlayerSurface(modifier) { playerView ->
    playerView.player = player

    lifecycle.addObserver(object : LifecycleObserver {
      @OnLifecycleEvent(Lifecycle.Event.ON_START)
      fun onStart() {
        playerView.keepScreenOn = true
        playerView.onResume()
        if (state.isPlaying) {
          controller.play()
        }
      }

      @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
      fun onStop() {
        playerView.keepScreenOn = false
        playerView.onPause()
        controller.pause()
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
  onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
  AndroidView(
    modifier = modifier,
    factory = { context ->
      PlayerView(context).apply {
        useController = false
        onPlayerViewAvailable(this)
      }
    }
  )
}
