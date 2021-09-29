package com.seiko.tv.anime.data.remote.response.sakura

import moe.tlaster.hson.annotations.HtmlSerializable

internal data class TimelineResponse(
  @HtmlSerializable("div.side div.tag > span")
  val tag: List<String> = emptyList(),
  @HtmlSerializable("div.side div.tlist > ul")
  val tagAnimesList: List<TagAnimes> = emptyList(),
) {

  class TagAnimes(
    @HtmlSerializable("li")
    val animes: List<Anime> = emptyList()
  ) {
    override fun toString(): String {
      return animes.toString()
    }
  }

  data class Anime(
    @HtmlSerializable("> a", attr = "title")
    val title: String = "",
    @HtmlSerializable("> a", attr = "href")
    val href: String = "",
    @HtmlSerializable("a")
    val body: String = "",
    @HtmlSerializable("a", attr = "href")
    val bodyHref: String = "",
  )
}