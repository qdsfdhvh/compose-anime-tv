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
package moe.tlaster.koin

import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.instance.newInstance
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : ViewModel> Module.viewModel(
  qualifier: Qualifier?,
  noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
  return factory(qualifier, definition)
}

@KoinReflectAPI
actual inline fun <reified T : ViewModel> Module.viewModel(
  qualifier: Qualifier?,
): Pair<Module, InstanceFactory<T>> {
  return factory(qualifier) { newInstance(T::class, it) }
}
