plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("com.google.devtools.ksp")
}

kotlin {
  android()
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
  }
  ios()
  sourceSets {
    val commonMain by getting {
      kotlin.srcDir("src/commonMain/accompanist")
      kotlin.srcDir("src/commonMain/material3")
      dependencies {
        // Compose
        api(compose.ui)
        api(compose.foundation)
        api(compose.animation)
        // @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        // api(compose.material3)
        implementation(compose.material)
        api(compose.materialIconsExtended)
        implementation("org.jetbrains.compose.ui:ui-util:${Versions.compose_jb}")
        // Di
        api("io.insert-koin:koin-core:${Versions.koin}")
        // kotlinx
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.serialization}")
        api("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Kotlin.deateTime}")
        // Navigator https://github.com/Tlaster/PreCompose
        api("moe.tlaster:precompose:1.2.3")
        // Paging https://github.com/qdsfdhvh/multiplatform-paging
        api("io.github.qdsfdhvh:paging:1.0.1")
        // Image https://github.com/qdsfdhvh/compose-imageloader
        api("io.github.qdsfdhvh:image-loader:1.0.6")
        // Log
        api("io.github.aakira:napier:${Versions.napier}")
      }
    }
    val androidMain by getting {
      dependencies {
        // Compose
        api("androidx.compose.material3:material3:${Versions.composeMaterial}")
        api("androidx.compose.material:material-icons-extended:${Versions.compose}")
        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}")
        // android
        api("androidx.core:core-ktx:${Versions.coreKtx}")
        api("androidx.activity:activity-ktx:${Versions.activity}")
      }
    }
    val jvmMain by getting {
      dependencies {
        // Di
        api("io.insert-koin:koin-core-jvm:${Versions.koin}")
        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.Kotlin.coroutines}")
      }
    }
    val iosMain by getting {
    }
  }
}

android {
  namespace = "${Package.applicationId}.core"
  compileSdk = AndroidSdk.compile
  defaultConfig {
    minSdk = AndroidSdk.min
  }
  compileOptions {
    sourceCompatibility = Versions.Java.java
    targetCompatibility = Versions.Java.java
  }
}
