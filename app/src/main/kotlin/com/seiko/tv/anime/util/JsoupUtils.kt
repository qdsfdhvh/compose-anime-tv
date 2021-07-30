package com.seiko.tv.anime.util

import com.seiko.tv.anime.model.Anime
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

private const val userAgent =
  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60"

fun getDocument(url: String): Document {
  return Jsoup.connect(url)
    .userAgent(userAgent)
    .get()
}

/**
 * 从<div class="img">中解析出动漫数据
 */
fun parseImgElement(img: Element, referer: String): List<Anime> {
  return img.select("ul").select("li").map { li ->
    val a = li.select("a")
    val href = a.attr("href")
    Anime(
      id = parseAnimeId(href),
      title = li.select("[class=tname]").text(),
      actionUrl = href,
      imageUrl = a.select("img").attr("src"),
      referer = referer
    )
  }
}

/**
 * 解析出动漫id
 */
fun parseAnimeId(href: String): Int {
  return "\\d+".toRegex().find(href)?.value?.toIntOrNull() ?: 0
}