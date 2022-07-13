package com.seiko.tv.anime.data.local.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "anime",
  indices = [Index(value = ["uri"], unique = true)]
)
data class DbAnime(
  @PrimaryKey(autoGenerate = true)
  val id: Long,
  val title: String,
  val cover: String,
  val uri: String,
  val updateAt: Long,
  val createAt: Long
)
