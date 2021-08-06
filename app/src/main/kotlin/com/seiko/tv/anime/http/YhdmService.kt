package com.seiko.tv.anime.http

import com.seiko.tv.anime.model.yhdm.DetailResponse
import com.seiko.tv.anime.model.yhdm.HomeResponse
import com.seiko.tv.anime.util.extensions.await
import moe.tlaster.hson.Hson
import okhttp3.OkHttpClient
import okhttp3.Request

private const val USER_AGENT =
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36 Edg/91.0.864.59"

class YhdmService(
  private val baseUrl: String,
  private val client: OkHttpClient,
) {

  suspend fun getHomeResponse(): HomeResponse {
    return Hson.deserializeKData(getHtml(""))
  }

  suspend fun getDetailResponse(animeId: Int): DetailResponse {
    return Hson.deserializeKData(getHtml("show/${animeId}.html"))
  }

  @Suppress("BlockingMethodInNonBlockingContext")
  private suspend fun getHtml(path: String): String {
    val request = Request.Builder()
      .header("User-Agent", USER_AGENT)
      .url(baseUrl + path)
      .get()
      .build()
    return client.newCall(request).await().body!!.string()
  }
}