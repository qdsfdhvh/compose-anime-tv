package com.seiko.tv.anime.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.asResult(): Flow<Result<T>> =
  this.map {
    Result.success(it)
  }.catch {
    Result.failure<T>(it)
  }

inline fun <T> Flow<Result<T>>.onSuccess(crossinline action: (value: T) -> Unit): Flow<Result<T>> =
  this.onEach {
    it.onSuccess(action)
  }

inline fun <T> Flow<Result<T>>.onFailure(crossinline action: (exception: Throwable) -> Unit): Flow<Result<T>> =
  this.onEach {
    it.onFailure(action)
  }
