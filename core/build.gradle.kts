plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = AndroidSdk.compile
}

dependencies {
  hilt()
  android()
  kotlinCoroutines()
  utils()
}
