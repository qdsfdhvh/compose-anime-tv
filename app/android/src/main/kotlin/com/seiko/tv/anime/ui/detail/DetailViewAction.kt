package com.seiko.tv.anime.ui.detail

sealed class DetailViewAction {
  data class Initial(val uri: String) : DetailViewAction()
  object ToggleFavorite : DetailViewAction()
}
