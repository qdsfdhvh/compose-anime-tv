package com.seiko.tv.anime.model.yhdm

import com.seiko.tv.anime.model.Anime
import moe.tlaster.hson.annotations.HtmlSerializable

data class HomeResponse(
  @HtmlSerializable(".img > ul > li")
  val animes: List<Anime>
)