package com.seiko.tv.anime.model.serializer

import moe.tlaster.hson.HtmlSerializer
import org.jsoup.nodes.Element

class TrimStringSerializer : HtmlSerializer<String> {
  override fun decode(element: Element, wholeText: String): String {
    return wholeText.split(":")[1].replace("\\s".toRegex(), "").trim()
  }
}