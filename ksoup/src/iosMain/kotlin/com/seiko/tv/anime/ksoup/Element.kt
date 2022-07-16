package com.seiko.tv.anime.ksoup

// TODO support ios
actual open class Element(
  private val element: Any,
) {
  actual fun select(query: String): Elements {
    TODO()
  }

  actual fun text(): String {
    TODO()
  }

  actual fun attr(attributeKey: String): String {
    TODO()
  }

  actual fun hasAttr(attributeKey: String): Boolean {
    TODO()
  }
}

actual class Document : Element(Unit)
