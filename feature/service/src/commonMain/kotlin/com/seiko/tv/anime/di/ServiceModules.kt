package com.seiko.tv.anime.di

import com.seiko.tv.anime.di.module.dbModule
import com.seiko.tv.anime.di.module.httpModule
import com.seiko.tv.anime.di.module.repositoryModule

val serviceModules = dbModule + httpModule + repositoryModule
