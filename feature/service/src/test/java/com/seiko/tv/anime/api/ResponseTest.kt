package com.seiko.tv.anime.api

import com.seiko.tv.anime.data.remote.response.sakura.DetailResponse
import com.seiko.tv.anime.data.remote.response.sakura.HomeResponse
import com.seiko.tv.anime.data.remote.response.sakura.TimelineResponse
import com.seiko.tv.anime.data.remote.response.sakura.VideoResponse
import kotlinx.coroutines.runBlocking
import moe.tlaster.hson.Hson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResponseTest {

  @Test
  fun homeTest() = runBlocking {
    val file = File("src/test/resources/api/sakura_home.html").readText()
    val response = Hson.deserializeKData<HomeResponse>(file)
    assert(response.titles.any())
    assert(response.titles[0].isNotEmpty())
    assert(response.groups.any())
    assert(response.groups[0].animes.any())
    assert(response.groups[0].animes[0].title.isNotEmpty())
    assert(response.titles.size == response.groups.size)
    assert(response.tabs.any())
    assert(response.tabs[0].title.isNotEmpty())
  }

  @Test
  fun homeType2Test() = runBlocking {
    val file = File("src/test/resources/api/sakura_home_type2.html").readText()
    val response = Hson.deserializeKData<HomeResponse>(file)
    assert(response.titles.any()) { response }
    assert(response.titles[0].isNotEmpty()) { response }
    assert(response.groups.any()) { response }
    assert(response.groups[0].animes.any()) { response }
    assert(response.groups[0].animes[0].title.isNotEmpty()) { response }
    assert(response.titles.size == response.groups.size) { response }
  }

  @Test
  fun detailTest() = runBlocking {
    val file = File("src/test/resources/api/sakura_detail.html").readText()
    assert(file.isNotEmpty())

    val response = Hson.deserializeKData<DetailResponse>(file)
    assert(response.title.isNotEmpty())
    assert(response.cover.isNotEmpty())
    assert(response.alias.isNotEmpty())
    assert(response.rating > 0)
    assert(response.releaseTime.isNotEmpty())
    assert(response.area.isNotEmpty())
    assert(response.types.isNotEmpty())
    assert(response.tags.isNotEmpty())
    assert(response.indexes.isNotEmpty())
    assert(response.state.isNotEmpty())
    assert(response.description.isNotEmpty())
    assert(response.episodeList.isNotEmpty())
    assert(response.relatedList.isNotEmpty())
  }

  @Test
  fun videoTest() = runBlocking {
    val file = File("src/test/resources/api/sakura_video.html").readText()
    assert(file.isNotEmpty())

    val response = Hson.deserializeKData<VideoResponse>(file)
    assert(response.playUrl.isNotEmpty())
    assert(response.playUrl.contains("$").not())
  }

  @Test
  fun timeLineTest() = runBlocking {
    val file = File("src/test/resources/api/sakura_home.html").readText()
    val response = Hson.deserializeKData<TimelineResponse>(file)
    assert(response.tags.isNotEmpty()) { response }
    assertEquals(response.tags.size, response.tagAnimesList.size)
    assert(response.tagAnimesList[0].animes[0].title.isNotEmpty()) { response }
  }
}
