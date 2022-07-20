package com.seiko.tv.anime.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val JSON: Json by lazy {
  Json {
    ignoreUnknownKeys = true
    isLenient = true
    coerceInputValues = true
  }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> T.toJson(): String {
  return JSON.encodeToString(this)
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> String.fromJson(): T {
  return JSON.decodeFromString(this)
}
