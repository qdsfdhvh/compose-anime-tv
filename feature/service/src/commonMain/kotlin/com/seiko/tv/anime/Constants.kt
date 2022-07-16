package com.seiko.tv.anime

const val YHDM_BAS_URL = "http://www.yhdm.so"

enum class OperatingSystem {
  Android, IOS, Windows, Linux, MacOS, Unknown;
}

expect val currentOperatingSystem: OperatingSystem
