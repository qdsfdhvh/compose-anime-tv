package com.seiko.tv.anime.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.seiko.tv.anime.model.anime.AnimeDetail
import com.seiko.tv.anime.repository.AnimeRepository
import com.seiko.tv.anime.util.asResult
import com.seiko.tv.anime.util.onFailure
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import moe.tlaster.koin.get

@Composable
fun DetailPresenter(
  event: Flow<DetailEvent>,
  uri: String,
  repository: AnimeRepository = get(),
): DetailState {
  var retryKey by remember { mutableStateOf(1) }
  val isFavorite by remember {
    repository.isFavoriteAnime(uri)
  }.collectAsState(false)
  val detailResult = remember(retryKey) {
    repository.getDetail(uri).asResult().onFailure {
      Napier.w(it) { "Detail error: " }
    }
  }.collectAsState(null).value
  LaunchedEffect(Unit) {
    event.collect { event ->
      when (event) {
        DetailEvent.Retry -> {
          retryKey++
        }
        DetailEvent.ToggleFavorite -> {
          detailResult?.onSuccess { detail ->
            if (isFavorite) {
              repository.removeFavoriteAnime(uri)
            } else {
              repository.insertFavoriteAnime(detail)
            }
          }
        }
      }
    }
  }
  return when (detailResult) {
    null -> DetailState.Loading
    else -> detailResult.fold(
      onSuccess = { detail ->
        DetailState.Success(
          detail = detail,
          isFavorite = isFavorite,
        )
      },
      onFailure = {
        DetailState.Error(
          message = it.message ?: it.toString(),
        )
      }
    )
  }
}

sealed class DetailEvent {
  object Retry : DetailEvent()

  object ToggleFavorite : DetailEvent()
}

sealed interface DetailState {
  object Loading : DetailState

  data class Error(
    val message: String,
  ) : DetailState

  data class Success(
    val detail: AnimeDetail,
    val isFavorite: Boolean,
  ) : DetailState
}
