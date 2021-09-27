package com.seiko.tv.anime.util

import androidx.compose.runtime.saveable.mapSaver

val indexSaver = mapSaver(
  save = { mapOf("index" to it) },
  restore = { it["index"] as? Int ?: 0 }
)
