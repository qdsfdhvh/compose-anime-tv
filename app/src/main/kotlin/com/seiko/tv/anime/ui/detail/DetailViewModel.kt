package com.seiko.tv.anime.ui.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.tv.anime.data.repository.AnimeRepository
import com.seiko.tv.anime.di.scope.IoDispatcher
import com.seiko.tv.anime.ui.composer.assisted.assistedViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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
import timber.log.Timber

class DetailViewModel @AssistedInject constructor(
  @Assisted uri: String,
  @IoDispatcher ioDispatcher: CoroutineDispatcher,
  repository: AnimeRepository,
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

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(uri: String): DetailViewModel
  }
}

@Composable
fun detailViewModel(uri: String): DetailViewModel {
  return assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(uri)
  }
}
