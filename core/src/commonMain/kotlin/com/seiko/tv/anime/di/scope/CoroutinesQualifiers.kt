package com.seiko.tv.anime.di.scope

import org.koin.core.qualifier.named

val defaultDispatcherName = named("DefaultDispatcher")
val ioDispatcherName = named("IoDispatcher")
val mainDispatcherName = named("MainDispatcher")
val mainImmediateDispatcherName = named("MainImmediateDispatcher")
