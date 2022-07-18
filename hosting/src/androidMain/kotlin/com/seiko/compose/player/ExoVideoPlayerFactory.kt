package com.seiko.compose.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class ExoVideoPlayerFactory(private val context: Context) : VideoPlayerFactory {
  override fun createPlayer(source: VideoPlayerSource): Player {
    return ExoPlayer.Builder(context)
      .build()
      .apply {
        val dataSourceFactory = DefaultDataSource.Factory(context)

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
