package com.seiko.tv.anime.injection.module

import com.seiko.tv.anime.ui.composer.screener.FpsSmallScreener
import com.seiko.tv.anime.ui.composer.screener.SmallScreener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet

@InstallIn(ActivityComponent::class)
@Module
object SmallScreenerModule {

  @Provides
  @IntoSet
  fun provideFpsSmallScreener(): SmallScreener = FpsSmallScreener
}
