package com.seiko.tv.anime.di

import com.seiko.tv.anime.di.module.dbModule
import com.seiko.tv.anime.di.module.httpModule
import com.seiko.tv.anime.di.module.repositoryModule
import com.seiko.tv.anime.di.module.storageModule

val serviceModules = storageModule + dbModule + httpModule + repositoryModule
