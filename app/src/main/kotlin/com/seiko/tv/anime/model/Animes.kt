package com.seiko.tv.anime.model

import moe.tlaster.hson.annotations.HtmlSerializable

sealed interface AnimeNode

data class Anime(
  @HtmlSerializable("img", attr = "alt")
  val title: String = "",
  @HtmlSerializable("img", attr = "src")
  val imageUrl: String = "",
  @HtmlSerializable("a", attr = "href")
  val actionUrl: String = ""
) : AnimeNode

data class AnimeGroup(
  val title: String,
  val animes: List<Anime>
)