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
package moe.tlaster.precompose.navigation.transition

import androidx.compose.ui.graphics.GraphicsLayerScope

val fadeCreateTransition: GraphicsLayerScope.(factor: Float) -> Unit = { factor ->
  alpha = factor
}
val fadeDestroyTransition: GraphicsLayerScope.(factor: Float) -> Unit = { factor ->
  alpha = factor
}

data class DialogTransition(
  /**
   * Transition the scene that about to appear for the first time, similar to activity onCreate, factor from 0.0 to 1.0
   */
  val createTransition: GraphicsLayerScope.(factor: Float) -> Unit = fadeCreateTransition,
  /**
   * Transition the scene that about to disappear forever, similar to activity onDestroy, factor from 1.0 to 0.0
   */
  val destroyTransition: GraphicsLayerScope.(factor: Float) -> Unit = fadeDestroyTransition,
)
