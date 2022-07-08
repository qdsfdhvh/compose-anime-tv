plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("com.google.devtools.ksp")
}

kotlin {
  android()
  jvm("desktop") {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        // Compose
        api(compose.ui)
        api(compose.uiTooling)
        api(compose.foundation)
        api(compose.animation)
        api(compose.material)
        api(compose.materialIconsExtended)

        // Di
        api("io.insert-koin:koin-core:${Versions.koin}")
        api("io.insert-koin:koin-core-jvm:${Versions.koin}")

        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}")

        // Serialization
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.serialization}")
      }
    }
    val androidMain by getting {
      dependencies {
        // Compose
        api("androidx.compose.material:material:${Versions.compose}")
        api("androidx.paging:paging-compose:${Versions.pagingCompose}")
        api("com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}")
        api("com.google.accompanist:accompanist-pager:${Versions.accompanist}")

        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}")

        // android
        api("androidx.core:core-ktx:${Versions.coreKtx}")
        api("androidx.activity:activity-ktx:${Versions.activity}")

        // Image
        api("io.coil-kt:coil-compose:${Versions.coil}")

        // Log
        api("com.jakewharton.timber:timber:${Versions.timber}")
      }
    }
    val desktopMain by getting {
      dependencies {
      }
    }
  }
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
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
