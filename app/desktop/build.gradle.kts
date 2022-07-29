import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  jvm("desktop") {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
    withJava()
  }
  sourceSets {
    val desktopMain by getting {
      dependencies {
        implementation(projects.core)
        implementation(projects.hosting)
        implementation(compose.desktop.currentOs)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "com.seiko.tv.anime.MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "AnimeTV"
      packageVersion = "1.0.0"
      modules("java.sql") // https://github.com/JetBrains/compose-jb/issues/381
      modules("jdk.unsupported")
      modules("jdk.unsupported.desktop")
      macOS {
        bundleID = Package.applicationId
        iconFile.set(project.file("src/desktopMain/resources/icon/ic_launcher.icns"))
      }
      linux {
        iconFile.set(project.file("src/desktopMain/resources/icon/ic_launcher.png"))
      }
      windows {
        shortcut = true
        menu = true
        iconFile.set(project.file("src/desktopMain/resources/icon/ic_launcher.ico"))
      }
    }
  }
}
