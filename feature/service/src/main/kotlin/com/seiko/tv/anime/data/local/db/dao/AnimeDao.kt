package com.seiko.tv.anime.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seiko.tv.anime.data.local.db.model.DbAnime

@Dao
interface AnimeDao {

  @Query("SELECT * FROM anime")
  fun findAll(): PagingSource<Int, DbAnime>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(anime: DbAnime)

  @Query("SELECT COUNT(1) FROM anime WHERE uri=:uri LIMIT 1")
  suspend fun contains(uri: String): Int
}
