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
package moe.tlaster.precompose.viewmodel

import java.io.Closeable

abstract class ViewModel {
  @Volatile
  private var disposed = false
  private val bagOfTags = hashMapOf<String, Any>()

  protected open fun onCleared() {}

  fun clear() {
    disposed = true
    bagOfTags.let {
      for (value in it.values) {
        disposeWithRuntimeException(value)
      }
    }
    onCleared()
  }

  open fun <T> setTagIfAbsent(key: String, newValue: T): T {
    @Suppress("UNCHECKED_CAST")
    return bagOfTags.getOrPut(key) {
      newValue as Any
    }.also {
      if (disposed) {
        disposeWithRuntimeException(it)
      }
    } as T
  }

  open fun <T> getTag(key: String): T? {
    @Suppress("UNCHECKED_CAST")
    return bagOfTags[key] as T?
  }

  private fun disposeWithRuntimeException(obj: Any) {
    if (obj is Closeable) {
      obj.close()
    }
  }
}
