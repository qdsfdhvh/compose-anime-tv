plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("de.mannodermaus.android-junit5")
}

android {
  compileSdk = AndroidSdk.compile
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}

dependencies {
  implementation(project(":core"))
  hilt()
  kotlinCoroutines()
  kotlinSerialization()
  network()
  junit5()
}
