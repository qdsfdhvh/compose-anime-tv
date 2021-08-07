package com.seiko.tv.anime.model

data class Anime(
  val title: String,
  val cover: String,
  val actionUrl: String
)

data class AnimeGroup(
  val title: String,
  val animes: List<Anime>
)

data class AnimeDetail(
  val title: String = "",
  val cover: String = "",
  val alias: String = "",
  val rating: Float = 0.0f,
  val releaseTime: String = "",
  val area: String = "",
  val types: List<String> = emptyList(),
  val tags: List<String> = emptyList(),
  val indexes: List<String> = emptyList(),
  val state: String = "",
  val description: String = "",
  val episodeList: List<AnimeEpisode> = emptyList(),
  val relatedList: List<Anime> = emptyList(),
)

data class AnimeEpisode(
  val title: String,
  val actionUrl: String,
)