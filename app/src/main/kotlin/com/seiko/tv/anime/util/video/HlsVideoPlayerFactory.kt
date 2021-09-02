package com.seiko.tv.anime.util.video

import android.content.Context
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.seiko.compose.player.VideoPlayerFactory
import com.seiko.compose.player.VideoPlayerSource
import com.seiko.compose.player.toMediaItem

object HlsVideoPlayerFactory : VideoPlayerFactory {
  override fun createPlayer(context: Context, source: VideoPlayerSource): Player {
    return SimpleExoPlayer.Builder(context)
      .build()
      .apply {
        val dataSourceFactory = DefaultDataSourceFactory(context)

        val mediaSource = if (source is VideoPlayerSource.Network && source.url.endsWith("m3u8")) {
          HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(source.toMediaItem())
        } else {
          ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(source.toMediaItem())
        }

        setMediaSource(mediaSource)
        prepare()
      }
  }
}
