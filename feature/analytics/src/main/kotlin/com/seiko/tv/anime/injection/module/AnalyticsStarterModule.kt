package com.seiko.tv.anime.injection.module

import com.seiko.tv.anime.util.starter.AppCenterStarter
import com.seiko.tv.anime.util.starter.BaseAppStarter
import com.seiko.tv.anime.util.starter.KOOMStarter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
interface AnalyticsStarterModule {

  @Binds
  @IntoSet
  fun bindAppCenterStarter(starter: AppCenterStarter): BaseAppStarter

  @Binds
  @IntoSet
  fun bindKOOMStarter(starter: KOOMStarter): BaseAppStarter
}
