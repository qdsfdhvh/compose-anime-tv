package com.seiko.tv.anime.mapper

import com.seiko.tv.anime.ksoup.Document
import com.seiko.tv.anime.ksoup.Element
import com.seiko.tv.anime.model.response.sakura.DetailResponse
import com.seiko.tv.anime.model.response.sakura.HomeResponse
import com.seiko.tv.anime.model.response.sakura.TagResponse
import com.seiko.tv.anime.model.response.sakura.TimelineResponse
import com.seiko.tv.anime.model.response.sakura.VideoResponse

internal fun Document.toHomeResponse(): HomeResponse {
  return HomeResponse(
    tabs = select(".menu ul.dmx > li").map { element ->
      val a = element.select("a")
      HomeResponse.AnimeTab(
        title = a.text(),
        href = a.attr("href")
      )
    },
    titles = select("div.firs div.dtit > h2 > a", "div.area div.dtit > h2").map { element ->
      element.text()
    },
    groups = select("div.firs div.img", "div.area div.imgs").map { element ->
      HomeResponse.AnimeGroup(
        animes = element.select(
          ".img > ul > li",
          "ul > li",
        ).map { element1 ->
          HomeResponse.Anime(
            title = element1.select("img").attr("alt"),
            cover = element1.select("img").attr("src"),
            href = element1.select("a").attr("href"),
          )
        }
      )
    },
  )
}

internal fun Document.toDetailResponse(): DetailResponse {
  return DetailResponse(
    title = select("h1").text(),
    cover = select("div.thumb > img").attr("src"),
    alias = select("div.sinfo > p").text().trimSplit(),
    rating = select("div.score > em").text().toFloatOrNull() ?: 0.0f,
    tags = select("div.sinfo > span").map { element ->
      DetailResponse.Tag(
        tip = element.text(),
        titles = element.select("a").map { it.text() },
        hrefs = element.select("a").map { it.attr("href") },
      )
    },
    description = select("div.info").text().trimS(),
    episodeList = select("div.movurl > ul > li").map { element ->
      DetailResponse.Episode(
        title = element.select("a").text(),
        href = element.select("a").attr("href"),
      )
    },
    relatedList = select("div.pics > ul > li").map { element ->
      DetailResponse.Anime(
        title = element.select("img").attr("alt"),
        cover = element.select("img").attr("src"),
        href = element.select("a").attr("href"),
      )
    }
  )
}

internal fun Document.toVideoResponse(): VideoResponse {
  return VideoResponse(
    playUrl = select("div.bofang > div").attr("data-vid").substringBefore('$'),
  )
}

internal fun Document.toTimelineResponse(): TimelineResponse {
  return TimelineResponse(
    tags = select("div.side div.tag > span").map { it.text() },
    tagAnimesList = select("div.side div.tlist > ul").map { element ->
      TimelineResponse.TagAnimes(
        animes = element.select("li").map { element1 ->
          TimelineResponse.Anime(
            title = element1.select("> a").attr("title"),
            href = element1.select("> a").attr("href"),
            body = element1.select("a").text(),
            bodyHref = element1.select("a").attr("href"),
          )
        }
      )
    },
  )
}

internal fun Document.toTagResponse(): TagResponse {
  return TagResponse(
    title = select("div.gohome > h1").text(),
    animes = select("div.lpic > ul > li").map { element ->
      TagResponse.Anime(
        title = element.select("h2 > a").attr("title"),
        href = element.select("h2 > a").attr("href"),
        cover = element.select("img").attr("src"),
        update = element.select("font").text(),
        tags = element.select("span").eq(1).let { element1 ->
          TagResponse.Tag(
            titles = element1.select("a").map { it.text() },
            hrefs = element1.select("a").map { it.attr("href") },
          )
        },
        description = element.select("p").text(),
      )
    }
  )
}

private fun Element.select(vararg selector: String): List<Element> {
  selector.forEach {
    val elements = select("$it:not(:root)")
    if (elements.any()) {
      return elements
    }
  }
  return emptyList()
}

private fun String.trimSplit(): String {
  val array = split(":")
  val value = if (array.size > 1) array[1] else this
  return value.trimS()
}

private fun String.trimS(): String {
  return replace("\\s".toRegex(), "").trim()
}
