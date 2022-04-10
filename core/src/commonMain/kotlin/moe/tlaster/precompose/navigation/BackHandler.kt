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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import moe.tlaster.precompose.ui.BackHandler
import moe.tlaster.precompose.ui.LocalBackDispatcherOwner
import moe.tlaster.precompose.ui.LocalLifecycleOwner

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
  // Safely update the current `onBack` lambda when a new one is provided
  val currentOnBack by rememberUpdatedState(onBack)
  // Remember in Composition a back callback that calls the `onBack` lambda
  val backCallback = remember {
    object : BackHandler {
      override fun handleBackPress(): Boolean {
        if (!enabled) return false
        currentOnBack.invoke()
        return true
      }
    }
  }

  val backDispatcher = checkNotNull(LocalBackDispatcherOwner.current) {
    "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
  }.backDispatcher
  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect(lifecycleOwner, backDispatcher) {
    // Add callback to the backDispatcher
    // backDispatcher.addCallback(lifecycleOwner, backCallback)
    backDispatcher.register(backCallback)
    // When the effect leaves the Composition, remove the callback
    onDispose {
      backDispatcher.unregister(backCallback)
    }
  }
}
