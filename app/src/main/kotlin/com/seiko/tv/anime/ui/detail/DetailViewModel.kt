package com.seiko.tv.anime.ui.detail

import androidx.compose.runtime.Composable
import com.seiko.tv.anime.data.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.koin.getViewModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class DetailViewModel(
  uri: String,
  ioDispatcher: CoroutineDispatcher,
  repository: AnimeRepository
) : ViewModel() {

  private val intents = Channel<DetailViewAction>(
    onBufferOverflow = BufferOverflow.DROP_OLDEST
  ).apply {
    trySend(DetailViewAction.Initial(uri))
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  val viewState: StateFlow<DetailViewState> = intents.receiveAsFlow()
    .scan(DetailViewState.Empty) { state, action ->
      when (action) {
        is DetailViewAction.Initial -> {
          state.copy(
            anime = repository.getDetail(action.uri),
            isFavorite = repository.isFavoriteAnime(uri)
          )
        }
        is DetailViewAction.ToggleFavorite -> {
          val isSuccess = if (state.isFavorite) {
            repository.removeFavoriteAnime(state.anime.uri)
          } else {
            repository.insertFavoriteAnime(state.anime)
          }
          if (isSuccess) state.copy(isFavorite = !state.isFavorite)
          else state
        }
      }
    }
    .flowOn(ioDispatcher)
    .catch { Timber.w(it, "Detail animeDetail error: ") }
    .stateIn(viewModelScope, SharingStarted.Lazily, DetailViewState.Empty)

  fun send(action: DetailViewAction) {
    intents.trySend(action)
  }
}

@Composable
fun detailViewModel(uri: String): DetailViewModel {
  return getViewModel {
    parametersOf(uri)
  }
}
