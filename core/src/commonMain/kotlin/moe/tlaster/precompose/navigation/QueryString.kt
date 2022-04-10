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

data class QueryString(
  private val rawInput: String,
) {
  val map by lazy {
    rawInput
      .split("?")
      .lastOrNull()
      .let { it ?: "" }
      .split("&")
      .asSequence()
      .map { it.split("=") }
      .filter { !it.firstOrNull().isNullOrEmpty() }
      .filter { it.size in 1..2 }
      .map { it[0] to it.elementAtOrNull(1) }
      .groupBy { it.first }
      .map { it.key to it.value.mapNotNull { it.second.takeIf { !it.isNullOrEmpty() } } }
      .toList()
      .toMap()
  }
}
