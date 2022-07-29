package com.seiko.tv.anime.di

import com.seiko.tv.anime.di.module.coroutinesDispatchersModule
import com.seiko.tv.anime.di.module.storageModule

val commonModules = coroutinesDispatchersModule + storageModule
