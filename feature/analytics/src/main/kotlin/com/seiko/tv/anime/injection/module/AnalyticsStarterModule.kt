package com.seiko.tv.anime.injection.module

import com.seiko.tv.anime.util.starter.AppCenterStarter
import com.seiko.tv.anime.util.starter.BaseAppStarter
import com.seiko.tv.anime.util.starter.KOOMStarter
import com.seiko.tv.anime.util.starter.TartStarter
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

  @Binds
  @IntoSet
  fun bindTartStarter(starter: TartStarter): BaseAppStarter
}
