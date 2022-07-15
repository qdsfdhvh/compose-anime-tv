package com.seiko.tv.anime.model.response.sakura

internal data class HomeResponse(
  val tabs: List<AnimeTab>,
  val titles: List<String>,
  val groups: List<AnimeGroup>
) {

  data class AnimeTab(
    val title: String = "",
    val href: String = "",
  )

  data class AnimeGroup(
    val animes: List<Anime> = emptyList(),
  )

  data class Anime(
    val title: String = "",
    val cover: String = "",
    val href: String = ""
  )
}
