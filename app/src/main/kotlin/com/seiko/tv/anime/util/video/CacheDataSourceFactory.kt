package com.seiko.tv.anime.util.video

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache

class CacheDataSourceFactory(
  private val context: Context,
  private val maxFileSize: Long,
) : DataSource.Factory {

  private val simpleCache: SimpleCache by lazy {
    VideoCache.getInstance(context)
  }

  private val defaultDatasourceFactory: DefaultDataSourceFactory

  init {
    val bandwidthMeter = DefaultBandwidthMeter.Builder(context).build()
    defaultDatasourceFactory = DefaultDataSourceFactory(
      context,
      bandwidthMeter,
      DefaultHttpDataSource.Factory()
        .setTransferListener(bandwidthMeter)
    )
  }

  override fun createDataSource(): DataSource {
    return CacheDataSource(
      simpleCache, defaultDatasourceFactory.createDataSource(),
      FileDataSource(), CacheDataSink(simpleCache, maxFileSize),
      CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null
    )
  }
}
