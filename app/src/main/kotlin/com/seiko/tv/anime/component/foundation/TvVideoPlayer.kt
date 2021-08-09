package com.seiko.tv.anime.component.foundation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.seiko.tv.anime.util.video.CacheDataSourceFactory

@Composable
fun TvVideoPlayer(url: String) {
  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle

  var autoPlay = remember(url) { true }
  var window = remember(url) { 0 }
  var position = remember(url) { 0L }

  val player = remember(url) {
    SimpleExoPlayer.Builder(context)
      .build()
      .apply {
        ProgressiveMediaSource.Factory(
          CacheDataSourceFactory(context, 100 * 1024 * 1024L)
        ).createMediaSource(MediaItem.fromUri(url)).let {
          setMediaSource(it)
          prepare()
        }

        playWhenReady = autoPlay
        seekTo(window, position)
      }
  }

  fun updateState() {
    autoPlay = player.playWhenReady
    window = player.currentWindowIndex
    position = 0L.coerceAtLeast(player.contentPosition)
  }

  DisposableEffect(url) {
    onDispose {
      updateState()
      player.release()
    }
  }

  AndroidView(
    modifier = Modifier.fillMaxSize(),
    factory = {
      StyledPlayerView(it).also { playerView ->
        lifecycle.addObserver(object : LifecycleObserver {
          @OnLifecycleEvent(Lifecycle.Event.ON_START)
          fun onStart() {
            playerView.keepScreenOn = true
            playerView.onResume()
            player.playWhenReady = autoPlay
          }

          @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
          fun onStop() {
            updateState()
            playerView.keepScreenOn = false
            playerView.onPause()
            player.playWhenReady = false
          }
        })
      }
    },
  ) { playerView ->
    playerView.player = player
  }
}