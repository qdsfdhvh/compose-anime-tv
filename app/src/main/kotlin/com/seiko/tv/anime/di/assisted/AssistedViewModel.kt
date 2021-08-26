package com.seiko.tv.anime.di.assisted

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seiko.tv.anime.LocalAssistedFactoryMap

typealias AssistedFactoryMap = Map<Class<out Any>, Any>

@Composable
inline fun <reified AF : Any, reified VM : ViewModel> assistedViewModel(
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
        return creator(factory as AF) as T
      }
    }
  )
}
