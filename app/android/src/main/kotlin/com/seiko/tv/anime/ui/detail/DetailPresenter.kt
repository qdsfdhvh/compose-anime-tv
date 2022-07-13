package com.seiko.tv.anime.ui.detail

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.platform.ioDispatcher
import com.seiko.tv.anime.model.anime.AnimeDetail
import com.seiko.tv.anime.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import moe.tlaster.koin.get

@SuppressLint("ComposableNaming")
@Composable
fun DetailPresenter(
  event: Flow<DetailEvent>,
  uri: String,
  repository: AnimeRepository = get(),
  dispatcher: CoroutineDispatcher = ioDispatcher,
): DetailState {
  var isFavorite by remember { mutableStateOf(false) }
  var detail by remember { mutableStateOf<AnimeDetail?>(null) }
  LaunchedEffect(Unit) {
    event.collect { event ->
      when (event) {
        DetailEvent.ToggleFavorite -> {
          if (detail == null) return@collect
          val success = withContext(dispatcher) {
            if (isFavorite) {
              repository.removeFavoriteAnime(uri)
            } else {
              repository.insertFavoriteAnime(detail!!)
            }
          }
          if (success) {
            isFavorite = !isFavorite
          }
        }
      }
    }
  }
  LaunchedEffect(Unit) {
    detail = repository.getDetail(uri)
  }
  LaunchedEffect(Unit) {
    isFavorite = repository.isFavoriteAnime(uri)
  }
  return when {
    detail != null -> {
      DetailState.Success(
        detail = detail!!,
        isFavorite = isFavorite,
      )
    }
    else -> DetailState.Loading
  }
}

sealed class DetailEvent {
  object ToggleFavorite : DetailEvent()
}

sealed interface DetailState {
  object Loading : DetailState
  data class Success(
    val detail: AnimeDetail,
    val isFavorite: Boolean,
  ) : DetailState
}
