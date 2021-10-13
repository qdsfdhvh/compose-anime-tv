package com.seiko.tv.anime.di.module

import android.app.Activity
import com.seiko.tv.anime.ui.composer.navigation.AppNavigator
import com.seiko.tv.anime.ui.composer.navigation.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object ActivityModule {

  @ActivityScoped
  @Provides
  fun provideAppNavigator(activity: Activity): AppNavigator {
    return AppNavigatorImpl(activity)
  }
}
