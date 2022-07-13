package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.YHDM_BAS_URL
import com.seiko.tv.anime.service.SakuraService
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.dsl.module

internal val httpModule = module {
  single {
    provideHttpClient()
  }
  single {
    SakuraService(YHDM_BAS_URL, get())
  }
}

private fun provideHttpClient(): HttpClient {
  return HttpClient(provideHttpClientEngine()) {
    install(HttpTimeout) {
      connectTimeoutMillis = 15000
      requestTimeoutMillis = 15000
    }
    install(Logging) {
      level = LogLevel.ALL
      logger = object : Logger {
        override fun log(message: String) {
          Napier.d(tag = "http") { message }
        }
      }
    }
  }
}

internal expect fun provideHttpClientEngine(): HttpClientEngine