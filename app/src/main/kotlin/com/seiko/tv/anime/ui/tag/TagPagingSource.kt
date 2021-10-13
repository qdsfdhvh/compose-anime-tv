package com.seiko.tv.anime.ui.tag

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seiko.tv.anime.data.model.anime.AnimeTagPageItem
import com.seiko.tv.anime.data.repository.AnimeRepository

class TagPagingSource(
  private val url: String,
  private val repository: AnimeRepository
) : PagingSource<Int, AnimeTagPageItem>() {

  override fun getRefreshKey(state: PagingState<Int, AnimeTagPageItem>): Int? = null

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeTagPageItem> {
    val page = params.key ?: 0
    return try {
      LoadResult.Page(
        data = repository.getTags(if (page == 0) url else "$url$page").animes,
        prevKey = if (page == 0) null else page - 1,
        nextKey = page + 1
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }
}
