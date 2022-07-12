package moe.tlaster.koin

import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : ViewModel> Module.viewModel(
  qualifier: Qualifier?
): Pair<Module, InstanceFactory<T>> {
  return factory(qualifier) { TODO() }
}
