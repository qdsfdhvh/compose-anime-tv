package com.seiko.tv.anime.data.remote.response.sakura

import com.seiko.tv.anime.util.serializer.TrimSplitStringSerializer
import com.seiko.tv.anime.util.serializer.TrimStringSerializer
import moe.tlaster.hson.annotations.HtmlSerializable

internal data class DetailResponse(
  @HtmlSerializable("h1")
  val title: String = "",
  @HtmlSerializable("div.thumb > img", attr = "src")
  val cover: String = "",
  @HtmlSerializable("div.sinfo > p", serializer = TrimSplitStringSerializer::class)
  val alias: String = "",
  @HtmlSerializable("div.score > em")
  val rating: Float = 0.0f,
  @HtmlSerializable("div.sinfo > span")
  val tags: List<Tag> = emptyList(),
  @HtmlSerializable("div.info", serializer = TrimStringSerializer::class)
  val description: String = "",
  @HtmlSerializable("div.movurl > ul > li")
  val episodeList: List<Episode> = emptyList(),
  @HtmlSerializable("div.pics > ul > li")
  val relatedList: List<Anime> = emptyList(),
) {

  data class Tag(
    @HtmlSerializable("")
    val tip: String = "",
    @HtmlSerializable("a")
    val titles: List<String> = emptyList(),
    @HtmlSerializable("a", attr="href")
    val hrefs: List<String> = emptyList()
  )

  data class Episode(
    @HtmlSerializable("a")
    val title: String = "",
    @HtmlSerializable("a", attr="href")
    val href: String = ""
  )

  data class Anime(
    @HtmlSerializable("img", attr = "alt")
    val title: String = "",
    @HtmlSerializable("img", attr = "src")
    val cover: String = "",
    @HtmlSerializable("a", attr = "href")
    val href: String = ""
  )
}
