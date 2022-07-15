package com.seiko.tv.anime.service

import com.seiko.tv.anime.ksoup.Ksoup
import com.seiko.tv.anime.mapper.toDetailResponse
import com.seiko.tv.anime.mapper.toHomeResponse
import com.seiko.tv.anime.mapper.toTagResponse
import com.seiko.tv.anime.mapper.toTimelineResponse
import com.seiko.tv.anime.mapper.toVideoResponse
import com.seiko.tv.anime.model.response.sakura.DetailResponse
import com.seiko.tv.anime.model.response.sakura.HomeResponse
import com.seiko.tv.anime.model.response.sakura.TagResponse
import com.seiko.tv.anime.model.response.sakura.TimelineResponse
import com.seiko.tv.anime.model.response.sakura.VideoResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText

private const val USER_AGENT =
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36 Edg/91.0.864.59"

class SakuraService(
  private val baseUrl: String,
  private val httpClient: HttpClient,
) {
  internal fun wrapUrl(href: String): String {
    return baseUrl + href
  }

  internal suspend fun getHomeResponse(url: String = wrapUrl("/")): HomeResponse {
    return Ksoup.parse(getHtml(url)).toHomeResponse()
  }

  internal suspend fun getDetailResponse(url: String): DetailResponse {
    return Ksoup.parse(getHtml(url)).toDetailResponse()
  }

  internal suspend fun getVideoResponse(url: String): VideoResponse {
    return Ksoup.parse(getHtml(url)).toVideoResponse()
  }

  internal suspend fun getTimeLineResponse(url: String = wrapUrl("/")): TimelineResponse {
    return Ksoup.parse(getHtml(url)).toTimelineResponse()
  }

  internal suspend fun getTagResponse(url: String): TagResponse {
    return Ksoup.parse(getHtml(url)).toTagResponse()
  }

  private suspend fun getHtml(url: String): String {
    return httpClient.get {
      url(url)
      header("User-Agent", USER_AGENT)
    }.bodyAsText()
  }
}
