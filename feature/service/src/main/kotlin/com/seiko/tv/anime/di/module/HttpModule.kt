package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.YHDM_BAS_URL
import com.seiko.tv.anime.data.service.SakuraService
import com.seiko.tv.anime.feature.service.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

internal val httpModule = module {
  single {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(
        HttpLoggingInterceptor().apply {
          setLevel(HttpLoggingInterceptor.Level.HEADERS)
        }
      )
    }
    builder.build()
  }
  single {
    val client = get<OkHttpClient>()
    SakuraService(YHDM_BAS_URL, client)
  }
}
