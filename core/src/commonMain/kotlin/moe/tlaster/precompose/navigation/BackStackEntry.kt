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
package moe.tlaster.precompose.navigation

import moe.tlaster.precompose.lifecycle.Lifecycle
import moe.tlaster.precompose.lifecycle.LifecycleOwner
import moe.tlaster.precompose.lifecycle.LifecycleRegistry
import moe.tlaster.precompose.navigation.route.ComposeRoute
import moe.tlaster.precompose.viewmodel.ViewModelStore
import moe.tlaster.precompose.viewmodel.ViewModelStoreOwner

class BackStackEntry internal constructor(
  val id: Long,
  val route: ComposeRoute,
  val pathMap: Map<String, String>,
  val queryString: QueryString? = null,
  internal val viewModel: NavControllerViewModel,
) : ViewModelStoreOwner, LifecycleOwner {
  private var destroyAfterTransition = false

  override val viewModelStore: ViewModelStore
    get() = viewModel.get(id = id)

  private val lifecycleRegistry: LifecycleRegistry by lazy {
    LifecycleRegistry()
  }

  override val lifecycle: Lifecycle
    get() = lifecycleRegistry

  fun active() {
    lifecycleRegistry.currentState = Lifecycle.State.Active
  }

  fun inActive() {
    lifecycleRegistry.currentState = Lifecycle.State.InActive
    if (destroyAfterTransition) {
      destroy()
    }
  }

  fun destroy() {
    if (lifecycleRegistry.currentState != Lifecycle.State.InActive) {
      destroyAfterTransition = true
    } else {
      lifecycleRegistry.currentState = Lifecycle.State.Destroyed
      viewModelStore.clear()
    }
  }

  inline fun <reified T> path(path: String): T {
    val value = requireNotNull(pathMap[path])
    return convertValue(value)
  }

  inline fun <reified T> query(name: String): T? {
    return query(name, null)
  }

  inline fun <reified T> query(name: String, default: T): T {
    val value = queryString?.map?.get(name)?.firstOrNull() ?: return default
    return convertValue(value)
  }

  inline fun <reified T> queryList(name: String): List<T> {
    val value = queryString?.map?.get(name) ?: return emptyList()
    return value.map { convertValue(it) }
  }
}

inline fun <reified T> convertValue(value: String): T {
  return when (T::class) {
    Int::class -> value.toIntOrNull()
    Long::class -> value.toLongOrNull()
    String::class -> value
    Boolean::class -> value.toBooleanStrictOrNull()
    Float::class -> value.toFloatOrNull()
    Double::class -> value.toDoubleOrNull()
    else -> throw NotImplementedError()
  } as T
}
