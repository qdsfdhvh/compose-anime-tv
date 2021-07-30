package com.seiko.tv.anime.http

import com.seiko.tv.anime.model.yhdm.HomeResponse
import com.seiko.tv.anime.util.extensions.await
import moe.tlaster.hson.Hson
import okhttp3.OkHttpClient
import okhttp3.Request

private const val USER_AGENT =
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36 Edg/91.0.864.59"

class YydmService(
  private val baseUrl: String,
  private val client: OkHttpClient,
) {

  @Suppress("BlockingMethodInNonBlockingContext")
  suspend fun getHomeResponse(path: String): HomeResponse {
    val request = Request.Builder()
      .header("User-Agent", USER_AGENT)
      .url(baseUrl + path)
      .get()
      .build()
    val html = client.newCall(request).await().body!!.string()
    return Hson.deserializeKData(html)
  }

}