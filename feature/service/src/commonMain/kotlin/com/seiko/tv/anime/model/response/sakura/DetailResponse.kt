package com.seiko.tv.anime.model.response.sakura

internal data class DetailResponse(
  val title: String = "",
  val cover: String = "",
  val alias: String = "",
  val rating: Float = 0.0f,
  val tags: List<Tag> = emptyList(),
  val description: String = "",
  val episodeList: List<Episode> = emptyList(),
  val relatedList: List<Anime> = emptyList(),
) {

  data class Tag(
    val tip: String = "",
    val titles: List<String> = emptyList(),
    val hrefs: List<String> = emptyList()
  )

  data class Episode(
    val title: String = "",
    val href: String = ""
  )

  data class Anime(
    val title: String = "",
    val cover: String = "",
    val href: String = ""
  )
}
