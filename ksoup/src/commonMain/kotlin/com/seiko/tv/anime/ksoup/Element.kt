package com.seiko.tv.anime.ksoup

import kotlin.jvm.JvmInline

expect open class Element {
  fun select(query: String): Elements

  fun text(): String

  fun attr(attributeKey: String): String

  fun hasAttr(attributeKey: String): Boolean
}

@JvmInline
value class Elements(
  private val elements: List<Element>
) : List<Element> by elements {
  fun text(): String {
    return elements.joinToString(" ") { it.text() }
  }

  fun attr(attributeKey: String): String {
    return elements.firstOrNull { it.hasAttr(attributeKey) }?.attr(attributeKey).orEmpty()
  }

  fun eq(index: Int): Element {
    return elements[index]
  }
}

expect class Document : Element
