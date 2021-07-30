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
    assert(response.animes.any())
    assert(response.animes[0].title.isNotEmpty())
  }
}