package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.di.scope.defaultDispatcherName
import com.seiko.tv.anime.di.scope.ioDispatcherName
import com.seiko.tv.anime.di.scope.mainDispatcherName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal actual val coroutinesDispatchersModule = module {
  single<CoroutineDispatcher>(defaultDispatcherName) { Dispatchers.Default }
  single<CoroutineDispatcher>(ioDispatcherName) { Dispatchers.IO }
  single<CoroutineDispatcher>(mainDispatcherName) { Dispatchers.Main }
}
