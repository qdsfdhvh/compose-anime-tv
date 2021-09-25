plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
  sourceSets {
    getByName("debug") {
      java.srcDirs("src/debug/kotlin")
    }
  }
}

dependencies {
  implementation(project(":core"))
  hilt()
  appCenter()

  // OOM https://github.com/KwaiAppTeam/KOOM
  val koomVersion = "2.0.0-beta1"
  implementation("com.kuaishou.koom:koom-native-leak:$koomVersion")
  implementation("com.kuaishou.koom:koom-monitor-base:$koomVersion")
  implementation("com.kuaishou.koom:koom-java-leak:$koomVersion")
  implementation("com.kuaishou.koom:koom-thread-leak:$koomVersion")

  // AppStart https://github.com/square/tart
  implementation("com.squareup.tart:tart:0.1")
}
