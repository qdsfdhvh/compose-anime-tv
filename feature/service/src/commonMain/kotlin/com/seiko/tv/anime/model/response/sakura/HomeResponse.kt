package com.seiko.tv.anime.model.response.sakura

import moe.tlaster.hson.annotations.HtmlSerializable

internal data class HomeResponse(
  @HtmlSerializable(".menu ul.dmx > li")
  val tabs: List<AnimeTab>,
  @HtmlSerializable(
    "div.firs div.dtit > h2 > a",
    "div.area div.dtit > h2",
  )
  val titles: List<String>,
  @HtmlSerializable(
    "div.firs div.img",
    "div.area div.imgs",
  )
  val groups: List<AnimeGroup>
) {

  data class AnimeTab(
    @HtmlSerializable("a")
    val title: String = "",
    @HtmlSerializable("a", attr = "href")
    val href: String = "",
  )

  data class AnimeGroup(
    @HtmlSerializable(
      ".img > ul > li",
      "ul > li"
    )
    val animes: List<Anime> = emptyList(),
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
