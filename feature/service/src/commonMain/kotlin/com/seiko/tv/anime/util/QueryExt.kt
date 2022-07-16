package com.seiko.tv.anime.util

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor

internal fun <T : Any, R : Any> Query<T>.flatMap(map: (T) -> R): Query<R> {
  return object : Query<R>(
    mapper = {
      val db = mapper.invoke(it)
      map.invoke(db)
    }
  ) {
    override fun <R> execute(mapper: (SqlCursor) -> R): QueryResult<R> {
      return this@flatMap.execute(mapper)
    }

    override fun addListener(listener: Listener) {
      this@flatMap.addListener(listener)
    }

    override fun removeListener(listener: Listener) {
      this@flatMap.removeListener(listener)
    }
  }
}
