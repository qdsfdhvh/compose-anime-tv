package com.seiko.tv.anime.injection.module

import com.seiko.tv.anime.util.starter.BaseAppStarter
import com.seiko.tv.anime.util.starter.StrictModeStarter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
interface AnalyticsDebugStarterModule {

  @Binds
  @IntoSet
  fun bindStrictModeStarter(starter: StrictModeStarter): BaseAppStarter
}
