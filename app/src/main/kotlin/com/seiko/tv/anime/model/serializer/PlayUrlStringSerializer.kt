package com.seiko.tv.anime.model.serializer

import moe.tlaster.hson.HtmlSerializer
import org.jsoup.nodes.Element

class PlayUrlStringSerializer : HtmlSerializer<String> {
  override fun decode(element: Element, wholeText: String): String {
    return wholeText.substring(0, wholeText.indexOf('$'))
  }
}