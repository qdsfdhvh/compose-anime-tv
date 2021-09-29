package com.seiko.tv.anime.util.starter

import tart.AppLaunch
import tart.PreLaunchState.ACTIVITY_WAS_STOPPED
import tart.PreLaunchState.NO_ACTIVITY_BUT_SAVED_STATE
import tart.PreLaunchState.NO_ACTIVITY_NO_SAVED_STATE
import tart.PreLaunchState.NO_PROCESS
import tart.PreLaunchState.NO_PROCESS_FIRST_LAUNCH_AFTER_CLEAR_DATA
import tart.PreLaunchState.NO_PROCESS_FIRST_LAUNCH_AFTER_INSTALL
import tart.PreLaunchState.NO_PROCESS_FIRST_LAUNCH_AFTER_UPGRADE
import tart.PreLaunchState.PROCESS_WAS_LAUNCHING_IN_BACKGROUND
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TartStarter @Inject constructor() : BaseAppStarter() {
  override fun run() {
    AppLaunch.onAppLaunchListeners += { appLaunch ->
      val startType = when (appLaunch.preLaunchState) {
        NO_PROCESS -> "cold start"
        NO_PROCESS_FIRST_LAUNCH_AFTER_INSTALL -> "cold start"
        NO_PROCESS_FIRST_LAUNCH_AFTER_UPGRADE -> "cold start"
        NO_PROCESS_FIRST_LAUNCH_AFTER_CLEAR_DATA -> "cold start"
        PROCESS_WAS_LAUNCHING_IN_BACKGROUND -> "warm start"
        NO_ACTIVITY_NO_SAVED_STATE -> "warm start"
        NO_ACTIVITY_BUT_SAVED_STATE -> "warm start"
        ACTIVITY_WAS_STOPPED -> "hot start"
      }
      val durationMillis = appLaunch.duration.uptime(TimeUnit.MILLISECONDS)
      Timber.tag("Tart").i("$startType launch: $durationMillis ms")
    }
  }
}
