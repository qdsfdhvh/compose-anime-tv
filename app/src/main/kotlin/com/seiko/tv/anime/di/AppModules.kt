package com.seiko.tv.anime.di

import com.seiko.tv.anime.di.module.imageLoaderModule
import com.seiko.tv.anime.di.module.viewModelModule

val appModules = imageLoaderModule + viewModelModule
