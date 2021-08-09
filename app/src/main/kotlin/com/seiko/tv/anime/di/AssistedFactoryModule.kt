package com.seiko.tv.anime.di

import com.seiko.tv.anime.di.assisted.ComposeAssistedFactory
import com.seiko.tv.anime.di.assisted.ComposeAssistedFactoryKey
import com.seiko.tv.anime.ui.detail.DetailViewModel
import com.seiko.tv.anime.ui.player.PlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
interface AssistedFactoryModule {

  @Binds
  @IntoMap
  @ComposeAssistedFactoryKey(DetailViewModel.AssistedFactory::class)
  fun bindDetailViewModelAssistedFactory(factory: DetailViewModel.AssistedFactory): ComposeAssistedFactory

  @Binds
  @IntoMap
  @ComposeAssistedFactoryKey(PlayerViewModel.AssistedFactory::class)
  fun bindPlayerViewModelAssistedFactory(factory: PlayerViewModel.AssistedFactory): ComposeAssistedFactory

}