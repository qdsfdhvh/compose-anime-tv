package com.seiko.tv.anime.di.module

import androidx.room.Room
import com.seiko.tv.anime.data.local.db.AnimeDataBase
import org.koin.dsl.module

internal val dbModule = module {
  single {
    Room.databaseBuilder(get(), AnimeDataBase::class.java, "app-anime-db")
      .build()
  }
}
