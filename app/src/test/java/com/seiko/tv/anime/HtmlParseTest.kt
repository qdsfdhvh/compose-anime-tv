package com.seiko.tv.anime

import com.seiko.tv.anime.util.parseAnimeId
import com.seiko.tv.anime.util.parseImgElement
import org.jsoup.Jsoup
import org.junit.Test

class HtmlParseTest {

  /**
   * 测试animeId提取
   */
  @Test
  fun testGetAnimeId() {
    val id = parseAnimeId("/show/5321.html")
    assert(id == 5321) { "parse anime id error, id=$id" }
  }

  /**
   * 测试对<div class="img">的解析
   */
  @Test
  fun testImgElementParse() {
    val html = """
    <div class="img">
      <ul>
        <li><a href="/show/5321.html"><img src="http://css.njhzmxx.com/acg/2021/07/16/20210716091847351.jpg"
              alt="定海浮生录"></a>
          <p class="tname"><a href="/show/5321.html">定海浮生录</a></p>
          <p>最新:<a target="_blank" href="/v/5321-4.html">第4集</a></p>
        </li>
        <li><a href="/show/5166.html"><img src="http://css.njhzmxx.com/down/1/131998959410264.jpg" alt="流星幻剑"></a>
          <p class="tname"><a href="/show/5166.html">流星幻剑</a></p>
          <p>最新:<a target="_blank" href="/v/5166-56.html">第56集</a></p>
        </li>
      </ul>
     </div>
    """.trimIndent()
    val img = Jsoup.parse(html).body()
    val animeList = parseImgElement(img, "")
    assert(animeList.isNotEmpty() && animeList[0].title.isNotEmpty())
  }
}