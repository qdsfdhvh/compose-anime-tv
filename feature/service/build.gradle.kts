plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("com.google.devtools.ksp")
  kotlin("plugin.serialization").version(Versions.Kotlin.lang)
}

kotlin {
  android()
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  sourceSets {
    val commonMain by getting {
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
        // ksp("androidx.room:room-compiler:${Versions.room}")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
  }
}

ksp {
  arg("room.schemaLocation", "$projectDir/schemas")
  arg("room.incremental", "true")
  arg("room.expandProjection", "true")
}

android {
  namespace = "com.seiko.tv.anime.feature.service"
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}
