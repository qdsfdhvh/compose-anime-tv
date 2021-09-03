package com.seiko.tv.anime.data.service

import com.seiko.tv.anime.data.remote.response.sakura.DetailResponse
import com.seiko.tv.anime.data.remote.response.sakura.HomeResponse
import com.seiko.tv.anime.data.remote.response.sakura.VideoResponse
import com.seiko.tv.anime.util.extensions.await
import moe.tlaster.hson.Hson
import okhttp3.OkHttpClient
import okhttp3.Request

private const val USER_AGENT =
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36 Edg/91.0.864.59"

class SakuraService(
  private val baseUrl: String,
  private val client: OkHttpClient,
) {

  internal fun wrapUrl(href: String): String {
    return baseUrl + href
  }

  internal suspend fun getHomeResponse(url: String = wrapUrl("/")): HomeResponse {
    return Hson.deserializeKData(getHtml(url))
  }

  internal suspend fun getDetailResponse(animeId: Int): DetailResponse {
    return Hson.deserializeKData(getHtml(wrapUrl("/show/$animeId.html")))
  }

  internal suspend fun getDetailResponse(episode: String): VideoResponse {
    return Hson.deserializeKData(getHtml(wrapUrl("/v/$episode.html")))
  }

  @Suppress("BlockingMethodInNonBlockingContext")
  private suspend fun getHtml(url: String): String {
    val request = Request.Builder()
      .header("User-Agent", USER_AGENT)
      .url(url)
      .get()
      .build()
    return client.newCall(request).await().body!!.string()
  }
}
