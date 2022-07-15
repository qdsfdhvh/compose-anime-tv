package com.seiko.tv.anime.api

import com.seiko.tv.anime.ksoup.Ksoup
import com.seiko.tv.anime.mapper.toDetailResponse
import com.seiko.tv.anime.mapper.toHomeResponse
import com.seiko.tv.anime.mapper.toTagResponse
import com.seiko.tv.anime.mapper.toTimelineResponse
import com.seiko.tv.anime.mapper.toVideoResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.File
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ResponseTest {

  @Test
  fun homeTest() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_home.html").readText()
    val response = Ksoup.parse(file).toHomeResponse()
    assert(response.titles.any())
    assertEquals(response.titles[0], "最新更新")
    assert(response.groups.any())
    assert(response.groups[0].animes.any())
    assertEquals(response.groups[0].animes[0].title, "我是大还丹")
    assert(response.titles.size == response.groups.size)
    assert(response.tabs.any())
    assertEquals(response.tabs[0].title, "首页")
  }

  @Test
  fun homeType2Test() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_home_type2.html").readText()
    val response = Ksoup.parse(file).toHomeResponse()
    assert(response.titles.any())
    assertEquals(response.titles[0], "好看的日本动漫")
    assert(response.groups.any())
    assert(response.groups[0].animes.any())
    assertEquals(response.groups[0].animes[0].title, "白沙的水族馆")
    assert(response.titles.size == response.groups.size)
  }

  @Test
  fun detailTest() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_detail.html").readText()
    assert(file.isNotEmpty())

    val response = Ksoup.parse(file).toDetailResponse()
    assertEquals(response.title, "小林家的龙女仆 第二季")
    assertEquals(response.cover, "http://css.njhzmxx.com/down/1/224537296736001.jpg")
    assertEquals(response.alias, "小林家的龙女仆S更新至5集")
    assertEquals(response.rating, 5.0f)
    assert(response.tags.isNotEmpty())
    assertEquals(response.tags.find { it.tip.startsWith("上映") }?.titles?.joinToString { it }, "2021")
    assertEquals(response.tags.find { it.tip.startsWith("地区") }?.titles?.joinToString { it }, "日本")
    assertContentEquals(response.tags.find { it.tip.startsWith("类型") }?.titles, listOf("搞笑", "奇幻", "百合", "治愈"))
    assertContentEquals(response.tags.find { it.tip.startsWith("标签") }?.titles, listOf("日语", "tv"))
    assertContentEquals(response.tags.find { it.tip.startsWith("索引") }?.titles, listOf("X动漫"))
    assertEquals(response.description, "《小林家的龙女仆第二季》主人公小林是一位女性系统工程师，某天她喝酒喝的很醉兴冲冲的跑到了山上遇到了一头龙，酒醉之下的小林对着龙大倒苦水。听到龙说自己无家可归时，小林趁着酒意开玩笑的说“那你就来我家吧”，之后龙真的就跑到小林的家里，并且还变成了一位女仆——于是一位龙女仆和小林的同居生活就这样开始了……")
    assertEquals(response.episodeList[0].title, "第05集")
    assertEquals(response.relatedList[0].title, "月光下的异世界之旅")
  }

  @Test
  fun videoTest() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_video.html").readText()
    assert(file.isNotEmpty())

    val response = Ksoup.parse(file).toVideoResponse()
    assert(response.playUrl.isNotEmpty())
    assert(response.playUrl.contains("$").not())
  }

  @Test
  fun timeLineTest() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_home.html").readText()
    val response = Ksoup.parse(file).toTimelineResponse()
    assert(response.tags.isNotEmpty()) { response }
    assertEquals(response.tags.size, response.tagAnimesList.size)
    assert(response.tagAnimesList[0].animes[0].title.isNotEmpty()) { response }
  }

  @Test
  fun tagTest() = runBlocking {
    val file = File("src/jvmTest/resources/api/sakura_tag.html").readText()
    val response = Ksoup.parse(file).toTagResponse()
    assertEquals(response.title, "机战动漫")
    assert(response.animes.isNotEmpty())
    val anime = response.animes[0]
    assertEquals(anime.title, "Muv-Luv Alternative")
    assertEquals(anime.cover, "http://css.njhzmxx.com/acg/2021/05/27/20210527051205790.jpg")
    assertEquals(anime.href, "/show/5272.html")
    assertEquals(anime.update, "更新至1集")
    assert(anime.tags.titles.isNotEmpty())
    assertEquals(anime.tags.titles.size, anime.tags.hrefs.size)
    assertEquals(anime.tags.titles[0], "科幻")
    assertEquals(anime.tags.hrefs[0], "/75/")
    assertEquals(anime.description, "电视动画《Muv-Luv Alternative》改编自Âge开发的同名游戏作品，2019年10月22日宣布了动画化的决定。2021年10月播出 那是在极限的世界战斗的人们之间牵绊的物语——存在于这一时空的，无数平行世界之一——在那...")
  }
}
