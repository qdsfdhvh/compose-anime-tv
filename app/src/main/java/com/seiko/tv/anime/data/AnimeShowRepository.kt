package com.seiko.tv.anime.data

import com.seiko.tv.anime.constant.YHDM_BAS_URL
import com.seiko.tv.anime.model.AnimeNode
import com.seiko.tv.anime.model.AnimeTitle
import com.seiko.tv.anime.util.extensions.scanApply
import com.seiko.tv.anime.util.getDocument
import com.seiko.tv.anime.util.parseImgElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AnimeShowRepository @Inject constructor() {

  /**
   * 约等于：
   *   val list = new ArrayList()
   *   for (children in area) {
   *     for (element in children) {
   *       val bean = parse(element)
   *       list.add(bean)
   *     }
   *   }
   *   list
   */
  @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
  fun getAnimeList(path: String = ""): Flow<List<AnimeNode>> {
    val url = YHDM_BAS_URL + path
    return flow { emit(getDocument(url)) }
      .map { document ->
        val area = document.getElementsByClass("area")
        if (path.isNotEmpty()) area else area.select("[class=firs l]") // 首页，有右边栏
      }
      .flatMapConcat { it.iterator().asFlow() }
      .flatMapMerge { it.children().asFlow() }
      .scanApply(mutableListOf<AnimeNode>()) { element ->
        when (element.className()) {
          "dtit" -> {
            val title = element.select("h2").text()
            add(AnimeTitle(title))
          }
          "img", "imgs" -> {
            addAll(parseImgElement(element, referer = url))
          }
        }
      }
      .flowOn(Dispatchers.IO)
  }
}