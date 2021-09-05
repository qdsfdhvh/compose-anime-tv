package com.seiko.tv.anime.ui.composer.assisted

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

typealias AssistedFactoryMap = Map<Class<out Any>, Any>

@Composable
inline fun <reified AF : Any, reified VM : ViewModel> assistedViewModel(
  vararg dependsOn: Any,
  noinline creator: ((AF) -> VM),
): VM {
  return assistedViewModel(
    factoryClass = AF::class.java,
    viewModelClass = VM::class.java,
    key = if (dependsOn.any()) {
      dependsOn.joinToString { it.hashCode().toString() } + VM::class.java.canonicalName
    } else null,
    creator = creator
  )
}

@Composable
fun <AF : Any, VM : ViewModel> assistedViewModel(
  factoryClass: Class<AF>,
  viewModelClass: Class<VM>,
  key: String?,
  creator: ((AF) -> VM)
): VM {
  val factoryMap = LocalAssistedFactoryMap.current
  val factory = factoryMap[factoryClass]!!
  return viewModel(
    modelClass = viewModelClass,
    key = key,
    factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return creator(factory as AF) as T
      }
    }
  )
}

@Composable
fun ProvideAssistedMap(
  assistedFactoryMap: AssistedFactoryMap,
  content: @Composable () -> Unit
) {
  CompositionLocalProvider(
    LocalAssistedFactoryMap provides assistedFactoryMap,
    content = content
  )
}

private val LocalAssistedFactoryMap = staticCompositionLocalOf<AssistedFactoryMap> { emptyMap() }
