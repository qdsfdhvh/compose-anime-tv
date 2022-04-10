package com.seiko.compose.player

import android.content.Context
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

interface VideoPlayerFactory {
  fun createPlayer(context: Context, source: VideoPlayerSource): Player

  companion object : VideoPlayerFactory {
    override fun createPlayer(context: Context, source: VideoPlayerSource): Player {
      return SimpleExoPlayer.Builder(context)
        .build()
        .apply {
          val dataSourceFactory = DefaultDataSourceFactory(context)

          val mediaSource = when {
            source is VideoPlayerSource.Network && source.url.endsWith("m3u8") -> {
              HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(source.toMediaItem())
            }
            else -> {
              ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(source.toMediaItem())
            }
          }

          setMediaSource(mediaSource)
          prepare()
        }
    }
  }
}
