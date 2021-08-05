package com.seiko.tv.anime.model.yhdm

import com.seiko.tv.anime.model.serializer.TrimStringSerializer
import moe.tlaster.hson.annotations.HtmlSerializable

data class DetailResponse(
  @HtmlSerializable("h1")
  val title: String = "",
  @HtmlSerializable("div.thumb > img", attr = "src")
  val cover: String = "",
  @HtmlSerializable("div.sinfo > p", serializer = TrimStringSerializer::class)
  val alias: String = "",
  @HtmlSerializable("div.score > em")
  val rating: Float = 0.0f,
  @HtmlSerializable("div.sinfo > span:nth-child(n+2)", serializer = TrimStringSerializer::class)
  val releaseTime: String = "",
  @HtmlSerializable("div.sinfo > span:nth-child(n+3)", serializer = TrimStringSerializer::class)
  val area: String = "",
  @HtmlSerializable("div.sinfo > span:nth-child(4) > a")
  val types: List<String> = emptyList(),
  @HtmlSerializable("div.sinfo > span:nth-child(5) > a")
  val tags: List<String> = emptyList(),
  @HtmlSerializable("div.sinfo > span:nth-child(6) > a")
  val indexes: List<String> = emptyList(),
  @HtmlSerializable("div.sinfo > p:nth-child(n+2)")
  val description: String = "",
)