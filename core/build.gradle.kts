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
  ios()
  sourceSets {
    val commonMain by getting {
      kotlin.srcDir("src/commonMain/third")
      dependencies {
        // Compose
        api(compose.ui)
        api(compose.foundation)
        api(compose.animation)
        implementation(compose.material)
        api(compose.materialIconsExtended)
        implementation("org.jetbrains.compose.ui:ui-util:${Versions.compose_jb}")
        // Di
        api("io.insert-koin:koin-core:${Versions.koin}")
        // Kotlinx
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}")
        api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.serialization}")
        api("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Kotlin.deateTime}")
        // Navigator https://github.com/Tlaster/PreCompose
        api("moe.tlaster:precompose:1.3.1")
        // Paging https://github.com/qdsfdhvh/multiplatform-paging
        api("io.github.qdsfdhvh:paging:1.0.4")
        // Image https://github.com/qdsfdhvh/compose-imageloader
        api("io.github.qdsfdhvh:image-loader:1.0.7")
        // TODO remove when material3 support ios
        // https://github.com/qdsfdhvh/compose-material3
        api("io.github.qdsfdhvh:material3:1.0.5")
        // Log
        api("io.github.aakira:napier:${Versions.napier}")
      }
    }
    val jvmMain by creating {
      dependsOn(commonMain)
    }
    val androidMain by getting {
      dependsOn(jvmMain)
      dependencies {
        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}")
        // Compose
        api("androidx.compose.ui:ui:${Versions.compose}")
        api("androidx.compose.foundation:foundation:${Versions.compose}")
        api("androidx.compose.material:material-icons-extended:${Versions.compose}")
        // Android
        api("androidx.core:core-ktx:${Versions.coreKtx}")
        api("androidx.activity:activity-ktx:${Versions.activity}")
      }
    }
    val desktopMain by getting {
      dependsOn(jvmMain)
      dependencies {
        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.Kotlin.coroutines}")
      }
    }
    val darwinMain by creating {
      dependsOn(commonMain)
    }
    val iosMain by getting {
      dependsOn(darwinMain)
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
