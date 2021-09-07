plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("com.google.devtools.ksp").version(Versions.ksp)
  id("de.mannodermaus.android-junit5")
}

android {
  compileSdk = AndroidSdk.compile
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
  defaultConfig {
    javaCompileOptions {
      annotationProcessorOptions {
        arguments += mapOf(
          "room.schemaLocation" to "$projectDir/schemas",
          "room.incremental" to "true",
          "room.expandProjection" to "true"
        )
      }
    }
  }
}

dependencies {
  implementation(project(":core"))
  hilt()
  kotlinCoroutines()
  kotlinSerialization()
  network()
  room()
  paging()
  junit5()
}
