package com.seiko.tv.anime.util.starter

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import javax.inject.Inject

class AppCenterStarter @Inject constructor(
  private val application: Application
) : BaseAppStarter() {

  override val runType: RunType = RunType.IDLE

  override fun run() {
    AppCenter.start(
      application,
      "907c7faf-2823-49ca-b777-64be1604ff5e",
      Analytics::class.java,
      Crashes::class.java
    )
  }
}
