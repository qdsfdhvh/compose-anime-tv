package com.seiko.tv.anime.model.anime

data class Anime(
  val title: String,
  val cover: String,
  val uri: String
)

data class AnimeTab(
  val title: String,
  val uri: String
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
  val types: List<AnimeTag> = emptyList(),
  val tags: List<AnimeTag> = emptyList(),
  val indexes: List<AnimeTag> = emptyList(),
  val state: String = "",
  val description: String = "",
  val episodeList: List<AnimeEpisode> = emptyList(),
  val relatedList: List<Anime> = emptyList(),
  val uri: String = ""
) {
  companion object {
    val Empty = AnimeDetail()
  }
}

data class AnimeTag(
  val title: String,
  val uri: String
)

data class AnimeEpisode(
  val title: String,
  val uri: String
)

data class AnimeVideo(
  val playUrl: String = ""
)

data class AnimeTimeLineGroup(
  val title: String,
  val animes: List<AnimeTimeLine>
)

data class AnimeTimeLine(
  val title: String,
  val uri: String,
  val body: String,
  val bodyUri: String
)

data class AnimeTagPage(
  val title: String,
  val animes: List<AnimeTagPageItem>
)

data class AnimeTagPageItem(
  val title: String,
  val cover: String,
  val uri: String,
  val update: String,
  val tags: List<AnimeTag>,
  val description: String
)
