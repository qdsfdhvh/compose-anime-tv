package com.seiko.tv.anime.ksoup

actual open class Element(
  private val element: org.jsoup.nodes.Element,
) {
  actual fun select(query: String): Elements {
    return element.select(query)
      .map { Element(it) }
      .let { Elements(it) }
  }

  actual fun text(): String {
    return element.text()
  }

  actual fun attr(attributeKey: String): String {
    return element.attr(attributeKey)
  }

  actual fun hasAttr(attributeKey: String): Boolean {
    return element.hasAttr(attributeKey)
  }
}

actual class Document(
  document: org.jsoup.nodes.Document,
) : Element(document)
