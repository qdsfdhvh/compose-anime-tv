package com.seiko.tv.anime.util.video

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object VideoCache {

  private const val MAX_CACHE_SIZE = 1024 * 1024 * 1024L

  private var simpleCache: SimpleCache? = null

  fun getInstance(context: Context): SimpleCache {
    if (simpleCache != null) {
      return simpleCache!!
    }

    val file = File(context.cacheDir, "media")
    if (!file.exists()) {
      file.mkdirs()
    }

    val cacheEvictor = LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE)
    simpleCache = SimpleCache(file, cacheEvictor, ExoDatabaseProvider(context))
    return simpleCache!!
  }
}
