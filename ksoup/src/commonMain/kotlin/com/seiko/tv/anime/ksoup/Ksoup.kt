package com.seiko.tv.anime.ksoup

expect object Ksoup {
  fun parse(html: String): Document
}
