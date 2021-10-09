plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("com.google.devtools.ksp").version(Versions.ksp)
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
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }
  sourceSets {
    getByName("debug") {
      java.srcDirs(
        "build/generated/ksp/debug/kotlin",
        "src/debug/kotlin",
      )
    }
  }
}

dependencies {
  implementation(project(":core"))
  hilt()
  appCenter()
  compose()
  "ksp"(project(":compiler:collectCompose"))

  // OOM https://github.com/KwaiAppTeam/KOOM
  val koomVersion = "2.0.0-beta1"
  implementation("com.kuaishou.koom:koom-native-leak:$koomVersion")
  implementation("com.kuaishou.koom:koom-monitor-base:$koomVersion")
  implementation("com.kuaishou.koom:koom-java-leak:$koomVersion")
  implementation("com.kuaishou.koom:koom-thread-leak:$koomVersion")

  // AppStart https://github.com/square/tart
  implementation("com.squareup.tart:tart:0.2")

  // Leak https://github.com/square/leakcanary
  debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

  // View Tree https://github.com/square/radiography
  implementation("com.squareup.radiography:radiography:2.4.1")
}
