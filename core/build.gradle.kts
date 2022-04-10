plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose").version(Versions.compose_jb)
  id("com.google.devtools.ksp").version(Versions.ksp)
}

kotlin {
  android()
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

        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}")

        // Serialization
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.serialization}")
      }
    }
    val androidMain by getting {
      dependencies {
        // Compose
        api("androidx.navigation:navigation-compose:${Versions.navigation}")
        api("androidx.paging:paging-compose:${Versions.pagingCompose}")
        api("com.google.accompanist:accompanist-insets:${Versions.accompanist}")
        api("com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}")
        api("com.google.accompanist:accompanist-pager:${Versions.accompanist}")

        // Di
        api("io.insert-koin:koin-android:${Versions.koin}")
        api("io.insert-koin:koin-androidx-compose:${Versions.koin}")

        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}")

        // android
        api("androidx.core:core-ktx:${Versions.coreKtx}")
        api("androidx.activity:activity-ktx:${Versions.activity}")
        api("androidx.activity:activity-compose:${Versions.activity}")
        api("io.coil-kt:coil-compose:${Versions.coil}")

        // Lifecycle
        api("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}")
        api("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
        api("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}")
        api("androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}")
        api("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}")

        // Log
        api("com.jakewharton.timber:timber:${Versions.timber}")
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
