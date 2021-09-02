plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = AndroidSdk.compile
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}

dependencies {
  hilt()
  android()
  kotlinCoroutines()
  utils()
}
