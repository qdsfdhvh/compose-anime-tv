package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.di.scope.ioDispatcher
import com.seiko.tv.anime.ui.detail.DetailViewModel
import com.seiko.tv.anime.ui.favorite.FavoriteViewModel
import com.seiko.tv.anime.ui.feed.FeedAnimeViewModel
import com.seiko.tv.anime.ui.feed.FeedViewModel
import com.seiko.tv.anime.ui.home.HomeViewModel
import com.seiko.tv.anime.ui.player.PlayerViewModel
import com.seiko.tv.anime.ui.tag.TagViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { (uri: String) -> DetailViewModel(uri, get(ioDispatcher), get()) }
  viewModel { FavoriteViewModel(get(ioDispatcher), get()) }
  viewModel { FeedViewModel(get()) }
  viewModel { (tab: AnimeTab) -> FeedAnimeViewModel(tab, get()) }
  viewModel { HomeViewModel() }
  viewModel { (uri: String) -> PlayerViewModel(uri, get()) }
  viewModel { (uri: String) -> TagViewModel(uri, get()) }
}