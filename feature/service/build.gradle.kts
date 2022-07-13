plugins {
  id("com.android.library")
  kotlin("android")
  id("com.google.devtools.ksp")
  kotlin("plugin.serialization").version(Versions.Kotlin.lang)
}

ksp {
  arg("room.schemaLocation", "$projectDir/schemas")
  arg("room.incremental", "true")
  arg("room.expandProjection", "true")
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
}

dependencies {
  implementation(projects.core)

  // Network
  implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
  implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}")
  implementation("com.github.qdsfdhvh:Hson:0.1.5")
  implementation("org.jsoup:jsoup:1.14.3")

  // Room
  implementation("androidx.room:room-runtime:${Versions.room}")
  implementation("androidx.room:room-ktx:${Versions.room}")
  implementation("androidx.room:room-paging:${Versions.room}") {
    exclude("androidx.paging")
  }
  ksp("androidx.room:room-compiler:${Versions.room}")

  // Test
  testImplementation(kotlin("test"))
}
