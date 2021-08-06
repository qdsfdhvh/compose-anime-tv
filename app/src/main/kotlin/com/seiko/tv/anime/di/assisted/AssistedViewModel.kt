package com.seiko.tv.anime.di.assisted

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import javax.inject.Provider

interface ComposeAssistedFactory

typealias AssistedFactoryMap = Map<Class<out ComposeAssistedFactory>, @JvmSuppressWildcards Provider<ComposeAssistedFactory>>

val LocalAssistedFactoryMap = staticCompositionLocalOf<AssistedFactoryMap> { emptyMap() }

@Composable
fun ProvideAssistedFactory(factoryMap: AssistedFactoryMap, content: @Composable () -> Unit) {
  CompositionLocalProvider(
    LocalAssistedFactoryMap provides factoryMap,
    content = content
  )
}

@Composable
inline fun <reified AF : ComposeAssistedFactory, reified VM : ViewModel> assistedViewModel(
  vararg dependsOn: Any,
  noinline creator: ((AF) -> VM),
): VM {
  val factoryMap = LocalAssistedFactoryMap.current
  val factory = factoryMap[AF::class.java]!!
  return viewModel(
    key = if (dependsOn.any()) {
      dependsOn.joinToString { it.hashCode().toString() } + VM::class.java.canonicalName
    } else {
      null
    },
    factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return creator(factory.get() as AF) as T
      }
    }
  )
}
