package com.seiko.tv.anime.util.starter

import android.app.Application
import com.kwai.koom.base.CommonConfig
import com.kwai.koom.base.MonitorManager
import com.kwai.koom.javaoom.monitor.OOMHprofUploader
import com.kwai.koom.javaoom.monitor.OOMMonitorConfig
import com.kwai.koom.javaoom.monitor.OOMReportUploader
import com.kwai.koom.nativeoom.leakmonitor.LeakListener
import com.kwai.koom.nativeoom.leakmonitor.LeakMonitorConfig
import com.kwai.koom.nativeoom.leakmonitor.LeakRecord
import com.kwai.performance.overhead.thread.monitor.ThreadLeakListener
import com.kwai.performance.overhead.thread.monitor.ThreadLeakRecord
import com.kwai.performance.overhead.thread.monitor.ThreadMonitorConfig
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.seiko.tv.anime.util.exception.AppLeakException
import java.io.File
import javax.inject.Inject
import timber.log.Timber


class KOOMStarter @Inject constructor(
  private val application: Application
) : BaseAppStarter() {
  override fun run() {
    val commonConfig = CommonConfig.Builder()
      .setApplication(application) // Set application
      .setVersionNameInvoker { "1.0.0" } // Set version name, java leak feature use it
      .build()
    MonitorManager.initCommonConfig(commonConfig)
      .apply { onApplicationCreate() }

    initJavaLeak()
    initNativeLeak()
    initThreadLeak()
  }

  private fun initJavaLeak() {
    val config = OOMMonitorConfig.Builder()
      .setEnableHprofDumpAnalysis(true)
      .setHprofUploader(object: OOMHprofUploader {
        override fun upload(file: File, type: OOMHprofUploader.HprofType) {
          Timber.tag(TAG).i("upload hprof: ${file.name}")

          Crashes.trackError(
            AppLeakException,
            mapOf(
              "type" to "app_oom_hprof",
            ),
            listOf(ErrorAttachmentLog.attachmentWithText(file.readText(), "koom.txt"))
          )
        }
      })
      .setReportUploader(object: OOMReportUploader {
        override fun upload(file: File, content: String) {
          Timber.tag(TAG).i(content)
          Timber.tag(TAG).i("upload report: ${file.name}")

          Crashes.trackError(
            AppLeakException,
            mapOf(
              "type" to "app_oom_report",
              "content" to "content"
            ),
            listOf(ErrorAttachmentLog.attachmentWithText(file.readText(), "koom.txt"))
          )
        }
      })
      .build()
    MonitorManager.addMonitorConfig(config)
  }

  private fun initNativeLeak() {
    val config = LeakMonitorConfig.Builder()
      .setLeakListener(object : LeakListener {
        override fun onLeak(leaks: MutableCollection<LeakRecord>) {
          if (leaks.isEmpty()) return
          Timber.tag(TAG).w(leaks.joinToString("\n"))
        }
      })
      .build()
    MonitorManager.addMonitorConfig(config)
  }

  private fun initThreadLeak() {
    val config = ThreadMonitorConfig.Builder()
      .enableThreadLeakCheck(2 * 1000L, 5 * 1000L)
      .setListener(object : ThreadLeakListener {
        override fun onError(msg: String) {
          Timber.tag(TAG).e(msg)
        }

        override fun onReport(leaks: MutableList<ThreadLeakRecord>) {
          Timber.tag(TAG).w(leaks.joinToString("\n"))
        }
      })
      .build()
    MonitorManager.addMonitorConfig(config)
  }

  companion object {
    private const val TAG = "KOOM"
  }
}