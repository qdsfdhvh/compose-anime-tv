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
}
