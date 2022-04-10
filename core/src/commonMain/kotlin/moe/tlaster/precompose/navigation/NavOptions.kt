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

/**
 * [NavOptions] stores special options for navigate actions
 */
data class NavOptions(
  /**
   * Whether this navigation action should launch as single-top (i.e., there will be at most
   * one copy of a given destination on the top of the back stack).
   */
  val launchSingleTop: Boolean = false,
  /**
   * The destination to pop up to before navigating. When set, all non-matching destinations
   * should be popped from the back stack.
   * @see [PopUpTo]
   */
  val popUpTo: PopUpTo? = null,
)
