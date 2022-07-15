package com.seiko.tv.anime.model.response.sakura

internal data class TimelineResponse(
  val tags: List<String> = emptyList(),
  val tagAnimesList: List<TagAnimes> = emptyList(),
) {

  data class TagAnimes(
    val animes: List<Anime> = emptyList()
  )

  data class Anime(
    val title: String = "",
    val href: String = "",
    val body: String = "",
    val bodyHref: String = "",
  )
}