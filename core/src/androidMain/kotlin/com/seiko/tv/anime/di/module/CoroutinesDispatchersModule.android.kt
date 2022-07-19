package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.di.scope.defaultDispatcherName
import com.seiko.tv.anime.di.scope.ioDispatcherName
import com.seiko.tv.anime.di.scope.mainDispatcherName
import com.seiko.tv.anime.di.scope.mainImmediateDispatcherName
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal actual val coroutinesDispatchersModule = module {
  single(defaultDispatcherName) { Dispatchers.Default }
  single(ioDispatcherName) { Dispatchers.IO }
  single(mainDispatcherName) { Dispatchers.Main }
  single(mainImmediateDispatcherName) { Dispatchers.Main.immediate }
}
