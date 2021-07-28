package com.seiko.tv.anime.util.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

@ExperimentalCoroutinesApi
fun <T, R> Flow<T>.scanApply(
  initial: R,
  operation: suspend R.(value: T) -> Unit
) = scan(initial) { r, v -> r.apply { operation(v) } }
