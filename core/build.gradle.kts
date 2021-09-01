plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
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
  hilt()
  hiltCompiler()
  utils()
}
