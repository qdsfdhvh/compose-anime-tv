package moe.tlaster.precompose

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositeKeyHash
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import moe.tlaster.precompose.ui.viewModel
import moe.tlaster.precompose.viewmodel.ViewModel

private typealias Awaiter = () -> Unit

private class PresenterViewModel<T : Any>(
  body: @Composable () -> T
) : ViewModel(), Awaiter {

  private val clock = BroadcastFrameClock(this)

  // compose snapshot only support main now
  private val scope = CoroutineScope(Dispatchers.Main) + clock

  val state: StateFlow<T> = scope.launchMolecule {
    body()
  }

  override fun onCleared() {
    scope.cancel()
  }

  override fun invoke() {
    clock.sendFrame(Clock.System.now().toEpochMilliseconds())
  }
}

@Composable
fun <T : Any> rememberPresenter(body: @Composable () -> T): StateFlow<T> {
  return viewModel(
    keys = listOf(
      currentCompositeKeyHash.toString(36),
    )
  ) { PresenterViewModel(body) }.state
}

private class EventViewModel<E> : ViewModel() {
  val channel = Channel<E>(capacity = Channel.BUFFERED)
  val pair = channel to channel.consumeAsFlow()
}

@Composable
fun <E> rememberEvent(): Pair<Channel<E>, Flow<E>> {
  return viewModel(
    keys = listOf(
      currentCompositeKeyHash.toString(36),
    )
  ) { EventViewModel<E>() }.pair
}
