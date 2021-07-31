package com.seiko.tv.anime.model.yhdm

import com.seiko.tv.anime.model.Anime
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
}