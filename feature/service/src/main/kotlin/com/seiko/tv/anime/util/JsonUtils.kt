package com.seiko.tv.anime.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val JSON by lazy {
  Json {
    ignoreUnknownKeys = true
    isLenient = true
    coerceInputValues = true
  }
}

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> T.toJson(): String {
  return JSON.encodeToString<T>(this)
}

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> String.fromJson(): T {
  return JSON.decodeFromString<T>(this)
}
