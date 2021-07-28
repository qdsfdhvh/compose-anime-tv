package com.seiko.tv.anime.model

sealed interface AnimeNode

data class Anime(
  val id: Int,
  val title: String = "",
  val referer: String = "",
  val imageUrl: String = "",
  val actionUrl: String = "",
) : AnimeNode

data class AnimeTitle(
  val title: String
) : AnimeNode