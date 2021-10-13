package com.seiko.tv.anime.data.remote.response.sakura

import moe.tlaster.hson.annotations.HtmlSerializable

internal data class TagResponse(
  @HtmlSerializable("div.gohome > h1")
  val title: String = "",
  @HtmlSerializable("div.lpic > ul > li")
  val animes: List<Anime> = emptyList()
) {
  data class Anime(
    @HtmlSerializable("h2 > a", attr="title")
    val title: String = "",
    @HtmlSerializable("img", attr="src")
    val cover: String = "",
    @HtmlSerializable("h2 > a", attr="href")
    val href: String = "",
    @HtmlSerializable("font")
    val update: String = "",
    @HtmlSerializable("span", eq = 1)
    val tags: Tag = Tag(),
    @HtmlSerializable("p")
    val description: String = "",
  )

  data class Tag(
    @HtmlSerializable("label")
    val titles: List<String> = emptyList(),
    @HtmlSerializable("a", attr="href")
    val hrefs: List<String> = emptyList()
  )
}