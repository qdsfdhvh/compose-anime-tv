package com.seiko.tv.anime.util.serializer

import moe.tlaster.hson.HtmlSerializer
import org.jsoup.nodes.Element

class TrimSplitStringSerializer : HtmlSerializer<String> {
  override fun decode(element: Element, wholeText: String): String {
    val array = wholeText.split(":")
    val value = if (array.size > 1) {
      array[1]
    } else {
      wholeText
    }
    return value.replace("\\s".toRegex(), "").trim()
  }
}
