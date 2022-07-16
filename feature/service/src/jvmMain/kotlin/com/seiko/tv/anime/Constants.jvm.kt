package com.seiko.tv.anime

actual val currentOperatingSystem: OperatingSystem
  get() {
    val sys = System.getProperty("os.name").lowercase()
    return when {
      sys.contains("win") -> {
        OperatingSystem.Windows
      }
      sys.contains("nix") ||
        sys.contains("nux") ||
        sys.contains("aix") -> {
        OperatingSystem.Linux
      }
      sys.contains("mac") -> {
        OperatingSystem.MacOS
      }
      else -> {
        OperatingSystem.Unknown
      }
    }
  }
