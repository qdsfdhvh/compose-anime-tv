package com.seiko.tv.anime.injection.module

import com.seiko.tv.anime.YHDM_BAS_URL
import com.seiko.tv.anime.data.service.SakuraService
import com.seiko.tv.anime.feature.service.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HttpModule {

  @Singleton
  @Provides
  fun provideOkhttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(
        HttpLoggingInterceptor().apply {
          setLevel(HttpLoggingInterceptor.Level.HEADERS)
        }
      )
    }
    return builder.build()
  }

  @Singleton
  @Provides
  fun provideSakuraService(client: OkHttpClient): SakuraService {
    return SakuraService(YHDM_BAS_URL, client)
  }
}
