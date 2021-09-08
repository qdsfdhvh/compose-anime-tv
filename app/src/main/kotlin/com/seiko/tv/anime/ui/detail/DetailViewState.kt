package com.seiko.tv.anime.ui.detail

import com.seiko.tv.anime.data.model.anime.AnimeDetail

data class DetailViewState(
  val anime: AnimeDetail,
  val isFavorite: Boolean
) {
  companion object {
    val Empty = DetailViewState(
      anime = AnimeDetail.Empty,
      isFavorite = false
    )
  }
}
