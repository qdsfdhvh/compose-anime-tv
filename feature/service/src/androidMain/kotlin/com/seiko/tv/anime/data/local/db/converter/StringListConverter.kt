package com.seiko.tv.anime.data.local.db.converter

import androidx.room.TypeConverter
import com.seiko.tv.anime.util.fromJson
import com.seiko.tv.anime.util.toJson

class StringListConverter {

  @TypeConverter
  fun fromString(value: String?): List<String>? {
    return value?.fromJson<List<String>>()
  }

  @TypeConverter
  fun fromList(list: List<String>?): String? {
    return list?.toJson()
  }
}
