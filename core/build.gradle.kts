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
  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }
}

dependencies {
  hilt()
  android()
  kotlinCoroutines()
  utils()
  compose()
  ksp(project(":compiler:collectCompose"))
}
