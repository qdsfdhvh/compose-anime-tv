package com.seiko.tv.anime.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seiko.tv.anime.data.local.db.converter.StringListConverter
import com.seiko.tv.anime.data.local.db.dao.AnimeDao
import com.seiko.tv.anime.data.local.db.model.DbAnime

@Database(
  entities = [
    DbAnime::class
  ],
  version = 1
)
@TypeConverters(
  StringListConverter::class
)
abstract class AnimeDataBase : RoomDatabase() {
  abstract fun animeDao(): AnimeDao
}
