package com.seiko.tv.anime.model.response.sakura

internal data class TagResponse(
  val title: String = "",
  val animes: List<Anime> = emptyList()
) {
  data class Anime(
    val title: String = "",
    val cover: String = "",
    val href: String = "",
    val update: String = "",
    val tags: Tag = Tag(),
    val description: String = "",
  )

  data class Tag(
    val titles: List<String> = emptyList(),
    val hrefs: List<String> = emptyList()
  )
}