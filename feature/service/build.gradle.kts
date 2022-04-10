plugins {
  id("com.android.library")
  kotlin("android")
  id("com.google.devtools.ksp").version(Versions.ksp)
  kotlin("plugin.serialization").version(Versions.Kotlin.lang)
}

ksp {
  arg("room.schemaLocation", "$projectDir/schemas")
  arg("room.incremental", "true")
  arg("room.expandProjection", "true")
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

  // Network
  implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
  implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}")
  implementation("com.github.qdsfdhvh:Hson:0.1.5") // https://github.com/Tlaster/Hson
  implementation("org.jsoup:jsoup:1.13.1") // https://github.com/jhy/jsoup/

  // Room
  implementation("androidx.room:room-runtime:${Versions.room}")
  implementation("androidx.room:room-ktx:${Versions.room}")
  implementation("androidx.room:room-paging:${Versions.room}")
  ksp("androidx.room:room-compiler:${Versions.room}")

  // Paging
  implementation("androidx.paging:paging-common-ktx:${Versions.paging}")

  // Test
  testImplementation(kotlin("test"))
}
