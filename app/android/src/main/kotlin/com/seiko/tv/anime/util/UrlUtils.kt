package com.seiko.tv.anime.util

import java.net.URLDecoder
import java.net.URLEncoder

fun String.encodeUrl(): String {
  return URLEncoder.encode(this, "utf-8")
}

fun String.decodeUrl(): String {
  return URLDecoder.decode(this, "utf-8")
}
