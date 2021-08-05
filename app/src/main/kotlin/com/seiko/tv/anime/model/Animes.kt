package com.seiko.tv.anime.model

sealed interface AnimeNode

data class Anime(
  val title: String,
  val cover: String,
  val actionUrl: String
) : AnimeNode

data class AnimeGroup(
  val title: String,
  val animes: List<Anime>
)