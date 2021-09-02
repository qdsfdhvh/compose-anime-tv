package com.seiko.tv.anime.data.remote.response.sakura

import moe.tlaster.hson.annotations.HtmlSerializable

data class HomeResponse(
  @HtmlSerializable(".firs div.dtit > h2 > a")
  val titles: List<String>,
  @HtmlSerializable(".firs div.img")
  val groups: List<AnimeGroup>
) {

  data class AnimeGroup(
    @HtmlSerializable(".img > ul > li")
    val animes: List<Anime> = emptyList(),
  )

  data class Anime(
    @HtmlSerializable("img", attr = "alt")
    val title: String = "",
    @HtmlSerializable("img", attr = "src")
    val cover: String = "",
    @HtmlSerializable("a", attr = "href")
    val actionUrl: String = ""
  )
}
