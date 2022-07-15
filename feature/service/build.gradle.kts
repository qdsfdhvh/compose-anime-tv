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
  // ios()
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.core)
        implementation(projects.ksoup)

        // Network
        implementation("io.ktor:ktor-client-logging:${Versions.ktor}")
        implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktor}")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")

        // Room
        implementation("androidx.room:room-runtime:${Versions.room}")
        implementation("androidx.room:room-ktx:${Versions.room}")
        implementation("androidx.room:room-paging:${Versions.room}") {
          exclude("androidx.paging")
        }
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
      }
    }
    // val iosMain by getting {
    //   dependencies {
    //     implementation("io.ktor:ktor-client-darwin:${Versions.ktor}")
    //   }
    // }
  }
}

dependencies {
  add("kspAndroid", "androidx.room:room-compiler:${Versions.room}")
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
