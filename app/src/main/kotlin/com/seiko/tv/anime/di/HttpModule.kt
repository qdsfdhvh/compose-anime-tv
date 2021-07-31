package com.seiko.tv.anime.di

import com.seiko.tv.anime.BuildConfig
import com.seiko.tv.anime.constant.YHDM_BAS_URL
import com.seiko.tv.anime.http.YydmService
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
          setLevel(HttpLoggingInterceptor.Level.BODY)
        }
      )
    }
    return builder.build()
  }

  @Singleton
  @Provides
  fun provideYhDmService(client: OkHttpClient): YydmService {
    return YydmService(YHDM_BAS_URL, client)
  }

}