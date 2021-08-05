package com.seiko.tv.anime.api

import com.seiko.tv.anime.model.yhdm.DetailResponse
import com.seiko.tv.anime.model.yhdm.HomeResponse
import kotlinx.coroutines.runBlocking
import moe.tlaster.hson.Hson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResponseTest {

  @Test
  fun homeTest() = runBlocking {
    val file = File("src/test/resources/api/yhdm_home.html").readText()
    assert(file.isNotEmpty())

    val response = Hson.deserializeKData<HomeResponse>(file)
    assert(response.titles.any())
    assert(response.titles[0].isNotEmpty())
    assert(response.groups.any())
    assert(response.groups[0].animes.any())
    assert(response.groups[0].animes[0].title.isNotEmpty())
    assert(response.titles.size == response.groups.size)
  }

  @Test
  fun detailTest() = runBlocking {
    val file = File("src/test/resources/api/yhdm_detail.html").readText()
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
    assert(response.description.isNotEmpty())
  }
}