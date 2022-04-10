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

import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.ViewModelStore
import moe.tlaster.precompose.viewmodel.getViewModel

internal class NavControllerViewModel : ViewModel() {
  private val viewModelStores = hashMapOf<Long, ViewModelStore>()

  fun clear(id: Long) {
    viewModelStores.remove(id)?.clear()
  }

  operator fun get(id: Long): ViewModelStore {
    return viewModelStores.getOrPut(id) {
      ViewModelStore()
    }
  }

  override fun onCleared() {
    viewModelStores.forEach {
      it.value.clear()
    }
    viewModelStores.clear()
  }

  companion object {
    fun create(viewModelStore: ViewModelStore): NavControllerViewModel {
      return viewModelStore.getViewModel {
        NavControllerViewModel()
      }
    }
  }
}
