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
package moe.tlaster.precompose.lifecycle

class LifecycleRegistry : Lifecycle {
  private val observers = arrayListOf<LifecycleObserver>()
  override var currentState: Lifecycle.State = Lifecycle.State.Initialized
    set(value) {
      if (field == Lifecycle.State.Destroyed || value == Lifecycle.State.Initialized) {
        return
      }
      field = value
      dispatchState(value)
    }

  private fun dispatchState(value: Lifecycle.State) {
    observers.toMutableList().forEach {
      it.onStateChanged(value)
    }
  }

  override fun removeObserver(observer: LifecycleObserver) {
    observers.remove(observer)
  }

  override fun addObserver(observer: LifecycleObserver) {
    observers.add(observer)
    observer.onStateChanged(currentState)
  }

  override fun hasObserver(): Boolean {
    return observers.isNotEmpty()
  }
}
