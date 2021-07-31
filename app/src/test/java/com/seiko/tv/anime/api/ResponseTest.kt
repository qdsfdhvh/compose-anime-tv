package com.seiko.tv.anime.api

import com.seiko.tv.anime.model.yhdm.HomeResponse
import kotlinx.coroutines.runBlocking
import moe.tlaster.hson.Hson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResponseTest {

  @Test
  fun userTest() = runBlocking {
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
}