plugins {
  id("com.android.library")
  kotlin("android")
}

android {
  compileSdk = AndroidSdk.compile
}

dependencies {
  compose()
  androidx()
  lifecycle()
  kotlinCoroutines()
  accompanist()
  navigation()
}