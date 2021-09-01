package com.seiko.tv.core.starter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Executor

abstract class BaseAppStarter : TaskInterface {

  override fun runOnExecutor(): Executor {
    return Dispatchers.IO.asExecutor()
  }
}