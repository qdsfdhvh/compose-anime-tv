package com.seiko.tv.anime.injection.module

import android.content.Context
import androidx.room.Room
import com.seiko.tv.anime.data.local.db.AnimeDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

  @Singleton
  @Provides
  fun provideAnimeDatabase(@ApplicationContext context: Context): AnimeDataBase {
    return Room.databaseBuilder(context, AnimeDataBase::class.java, "app-anime-db")
      .build()
  }
}
