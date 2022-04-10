/*
 *  Mask-Android
 *
 *  Copyright (C) 2022  DimensionDev and Contributors
 *
 *  This file is part of Mask-Android.
 *
 *  Mask-Android is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mask-Android is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with Mask-Android.  If not, see <http://www.gnu.org/licenses/>.
 */
package moe.tlaster.koin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import moe.tlaster.precompose.ui.LocalViewModelStoreOwner
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.ViewModelStoreOwner
import moe.tlaster.precompose.viewmodel.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> getViewModel(
  qualifier: Qualifier? = null,
  owner: ViewModelStoreOwner = LocalViewModelStoreOwner.current,
  scope: Scope = GlobalContext.get().scopeRegistry.rootScope,
  noinline parameters: ParametersDefinition? = null,
): T {
  return remember(qualifier, scope, parameters) {
    owner.getViewModel(qualifier, scope, parameters)
  }
}

// inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(
//     qualifier: Qualifier? = null,
//     mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
//     noinline parameters: ParametersDefinition? = null,
// ): Lazy<T> {
//     return lazy(mode) {
//         getViewModel<T>(qualifier, parameters)
//     }
// }
//
// fun <T : ViewModel> ViewModelStoreOwner.viewModel(
//     qualifier: Qualifier? = null,
//     clazz: KClass<T>,
//     mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
//     parameters: ParametersDefinition? = null,
// ): Lazy<T> {
//     return lazy(mode) { getViewModel(qualifier, clazz, parameters) }
// }

@OptIn(KoinInternalApi::class)
inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(
  qualifier: Qualifier? = null,
  scope: Scope = GlobalContext.get().scopeRegistry.rootScope,
  noinline parameters: ParametersDefinition? = null,
): T {
  return getViewModel(qualifier, T::class, scope, parameters)
}

@OptIn(KoinInternalApi::class)
fun <T : ViewModel> ViewModelStoreOwner.getViewModel(
  qualifier: Qualifier? = null,
  clazz: KClass<T>,
  scope: Scope = GlobalContext.get().scopeRegistry.rootScope,
  parameters: ParametersDefinition? = null,
): T {
  return this.viewModelStore.getViewModel(
    key = qualifier?.value ?: (clazz.toString() + parameters?.invoke()),
    clazz = clazz
  ) {
    scope.get(clazz, qualifier, parameters)
  }
}
