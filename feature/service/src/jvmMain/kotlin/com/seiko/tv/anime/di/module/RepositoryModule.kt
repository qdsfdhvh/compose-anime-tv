package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.data.repository.AnimeRepository
import com.seiko.tv.anime.di.scope.ioDispatcher
import org.koin.dsl.module

val repositoryModule = module {
  single { AnimeRepository(get(), get(), get(ioDispatcher)) }
}
